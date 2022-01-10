package com.piu.solar_energy_usage.invoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.piu.solar_energy_usage.R

class CardDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)

        val btnBack = findViewById<Button>(R.id.btn_back_card_details)
        btnBack.setOnClickListener {
            finish()
        }

        val invoice = intent.getSerializableExtra("invoice") as Invoice

        val payButton = findViewById<Button>(R.id.payButton)
        val cardNumber = findViewById<TextInputEditText>(R.id.cardNumberInput)
        val securityCode = findViewById<TextInputEditText>(R.id.securityCodeInput)
        val cardholderName = findViewById<TextInputEditText>(R.id.cardholerNameInput)
        val expirationDate = findViewById<TextInputEditText>(R.id.expirationDateInput)

        val cardNumberLayout = findViewById<TextInputLayout>(R.id.cardNumber)
        val securityCodeLayout = findViewById<TextInputLayout>(R.id.securityCode)
        val expirationDateLayout = findViewById<TextInputLayout>(R.id.expirationDate)
        val cardholderNameLayout = findViewById<TextInputLayout>(R.id.cardholerName)

        payButton.setOnClickListener {
            var ok = true

            val cardNumberResult = validateCardNumber(cardNumber.text.toString())
            if (!cardNumberResult.first) {
                cardNumberLayout.isErrorEnabled = true
                ok = false

                when(cardNumberResult.second) {
                    0 -> { cardNumberLayout.error = "card number is too short" }
                    1 -> { cardNumberLayout.error = "card number is too long" }
                }
            } else {
                cardNumberLayout.isErrorEnabled = false
            }

            val securityCodeResult = validateSecurityCode(securityCode.text.toString())
            if (!securityCodeResult.first) {
                securityCodeLayout.isErrorEnabled = true
                ok = false

                when(securityCodeResult.second) {
                    0 -> { securityCodeLayout.error = "security code is too short" }
                    1 -> { securityCodeLayout.error = "security code is too long" }
                }
            } else {
                securityCodeLayout.isErrorEnabled = false
            }

            val expirationDateResult = validateDate(expirationDate.text.toString())
            if (!expirationDateResult) {
                expirationDateLayout.isErrorEnabled = true
                ok = false

                expirationDateLayout.error = "expiration date is incorrect"
            } else {
                expirationDateLayout.isErrorEnabled = false
            }

            val cardholderResult = validateCardholderName(cardholderName.text.toString())
            if (!cardholderResult) {
                cardholderNameLayout.isErrorEnabled = true
                ok = false

                cardholderNameLayout.error = "cardholder name is incorrect"
            } else {
                cardholderNameLayout.isErrorEnabled = false
            }

            if (ok) {
                for(inv in Util.dataSource) {
                    if (inv.number == invoice.number) {
                        inv.isPaid = invoice.isPaid
                    }
                }

                val intent = Intent(this, InvoiceActivity::class.java)
                intent.putExtra("invoice", invoice)
                startActivity(intent)
                setResult(RESULT_OK, intent)
            }
        }
    }

    private fun validateCardNumber(cardNumber: String): Pair<Boolean, Int> {
        if (cardNumber.length < 16)
            return Pair(false, 0)

        if (cardNumber.length > 16)
            return Pair(false, 1)

        return Pair(true, -1)
    }

    private fun validateSecurityCode(securityCode: String): Pair<Boolean, Int> {
        if (securityCode.length < 3)
            return Pair(false, 0)

        if (securityCode.length > 3)
            return Pair(false, 1)

        return Pair(true, -1)
    }

    private fun validateDate(date: String): Boolean {
        if (date.length == 5)
            return true

        return false
    }

    private fun validateCardholderName(name: String): Boolean {
        return name.isNotEmpty()
    }
}