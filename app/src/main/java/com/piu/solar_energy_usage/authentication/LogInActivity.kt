package com.piu.solar_energy_usage.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.piu.solar_energy_usage.MainActivity
import com.piu.solar_energy_usage.MainWindowActivity
import com.piu.solar_energy_usage.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val usernameRef = findViewById<TextView>(R.id.usernameInput)
        val passwordRef = findViewById<TextView>(R.id.passwordInput)

        val button = findViewById<Button>(R.id.loginButton)
        button.setOnClickListener {
            val username = usernameRef.text
            val password = passwordRef.text

            var ok = true

            val usernameError = findViewById<TextView>(R.id.usernameError)
            val passwordError = findViewById<TextView>(R.id.passwordError)


            val usernameResult = validateUsername(username.toString())
            if(!usernameResult.first) {
                usernameError.visibility = View.VISIBLE

                ok = false

                when(usernameResult.second) {
                    0 -> { usernameError.text = "Username is too short" }
                    1 -> { usernameError.text = "Username is empty" }
                    2 -> { usernameError.text = "Username is not valid" }
                }

            } else {
                usernameError.visibility = View.GONE
            }

            val passwordResult = validatePassword(password.toString())
            if(!passwordResult.first) {
                passwordError.visibility = View.VISIBLE

                ok = false

                when(passwordResult.second) {
                    0 -> { passwordError.text = "Password is too short" }
                    1 -> { passwordError.text = "Password is empty" }
                    2 -> { passwordError.text = "Password is not valid" }
                }

            } else {
                passwordError.visibility = View.GONE
            }

            if(ok) {
                val intent = Intent(this, MainWindowActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun validateUsername(username: String): Pair<Boolean, Int> {
        if(username.length < 3) {
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
        if(password.length < 3) {
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