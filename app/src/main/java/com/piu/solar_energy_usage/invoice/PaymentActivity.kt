package com.piu.solar_energy_usage.invoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.piu.solar_energy_usage.R

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val invoice = intent.getSerializableExtra("invoice") as Invoice

        val buttonCard = findViewById<ImageButton>(R.id.pickCard)
        val buttonGoogle = findViewById<ImageButton>(R.id.pickGoogle)

        buttonCard.setOnClickListener {
            val intent = Intent(this, CardDetailsActivity::class.java)
            intent.putExtra("invoice", invoice)
            startActivity(intent)
        }

        buttonGoogle.setOnClickListener {
            invoice.isPaid = true
            val intent = Intent(this, InvoiceActivity::class.java)
            intent.putExtra("invoice", invoice)
            setResult(RESULT_OK, intent)
            super.onBackPressed()
        }
    }
}