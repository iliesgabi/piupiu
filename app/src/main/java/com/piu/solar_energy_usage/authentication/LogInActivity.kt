package com.piu.solar_energy_usage.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.piu.solar_energy_usage.MainWindowActivity
import com.piu.solar_energy_usage.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val usernameInput = findViewById<TextView>(R.id.usernameInput)
        val passwordInput = findViewById<TextView>(R.id.passwordInput)

        val button = findViewById<Button>(R.id.loginButton)
        button.setOnClickListener {
            val username = usernameInput.text
            val password = passwordInput.text

            var ok = true

            val usernameTextInputLayout = findViewById<TextInputLayout>(R.id.username)
            val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.password)

            val usernameResult = validateUsername(username.toString())
            if(!usernameResult.first) {
                usernameTextInputLayout.isErrorEnabled = true

                ok = false

                when(usernameResult.second) {
                    0 -> { usernameTextInputLayout.error = "Username is too short" }
                    1 -> { usernameTextInputLayout.error = "Username is empty" }
                    2 -> { usernameTextInputLayout.error = "Username is not valid" }
                }
            } else {
                usernameTextInputLayout.isErrorEnabled = false
            }

            val passwordResult = validatePassword(password.toString())
            if(!passwordResult.first) {
                passwordTextInputLayout.isErrorEnabled = true

                ok = false

                when(passwordResult.second) {
                    0 -> { passwordTextInputLayout.error = "Password is too short" }
                    1 -> { passwordTextInputLayout.error = "Password is empty" }
                    2 -> { passwordTextInputLayout.error = "Password is not valid" }
                }

            } else {
                passwordTextInputLayout.isErrorEnabled = false
            }

            if(ok) {
                val intent = Intent(this, MainWindowActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateUsername(username: String): Pair<Boolean, Int> {
        if(username.length in 1..2) {
            return Pair(false, 0)
        }

        if(username.isEmpty()) {
            return Pair(false, 1)
        }

        if(username == "admin") {
            return Pair(true, -1)
        }

        return Pair(false, 2)
    }

    private fun validatePassword(password: String): Pair<Boolean, Int> {
        if(password.length in 1..2) {
            return Pair(false, 0)
        }

        if(password.isEmpty()) {
            return Pair(false, 1)
        }

        if(password == "admin") {
            return Pair(true, -1)
        }

        return Pair(false, 2)
    }
}