package com.piu.solar_energy_usage.provider

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.piu.solar_energy_usage.R
import com.piu.solar_energy_usage.provider.model.Detail

class ProviderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_detail)

        val backBtn = findViewById<Button>(R.id.btn_back_collapsed_provider)
        backBtn.setOnClickListener {
            finish()
        }

        val name = intent.extras!!.getString(Detail.NAME).toString()
        val contact = intent.extras!!.getString(Detail.CONTACT).toString()
        val description = intent.extras!!.getString(Detail.DESCRIPTION).toString()

        val image = findViewById<AppCompatImageView>(R.id.iv_collapsed_provider)
        image.setBackgroundResource(Util.getProviderIcon(name))

        val tvDescription = findViewById<TextView>(R.id.tv_collapsed_provider_description)
        tvDescription.text = description

        val tvContact = findViewById<TextView>(R.id.tv_collapsed_provider_contact)
        tvContact.text = "@ Contact: ${contact}"

        val btnContract = findViewById<Button>(R.id.btn_contract)
        btnContract.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Request Information")
                .setMessage("The company was announced and they will be contacting you shortly!")
                .setPositiveButton("Ok") { _, _ ->
                    finish()
                }
                .create()
                .show()
        }

    }
}