package com.piu.solar_energy_usage.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.piu.solar_energy_usage.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<Button>(R.id.btn_back_register)
        btnBack.setOnClickListener {
            finish()
        }

        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {

            var ok = true

            val nameInput = findViewById<TextView>(R.id.registerNameInput)
            val nameInputLayout = findViewById<TextInputLayout>(R.id.registerNameInputLayout)
            val nameResult = validateName(nameInput.text.toString())

            if (!nameResult.first) {
                nameInputLayout.isErrorEnabled = true

                ok = false

                when (nameResult.second) {
                    1 -> {
                        nameInputLayout.error = "Name is too short"
                    }
                    2 -> {
                        nameInputLayout.error = "Name is empty"
                    }
                    3 -> {
                        nameInputLayout.error = "Invalid name format"
                    }
                }
            } else {
                nameInputLayout.isErrorEnabled = false
            }


            val usernameInput = findViewById<TextView>(R.id.registerUsernameInput)
            val usernameInputLayout =
                findViewById<TextInputLayout>(R.id.registerUsernameInputLayout)
            val usernameResult = validateUsername(usernameInput.text.toString())

            if (!usernameResult.first) {
                usernameInputLayout.isErrorEnabled = true

                ok = false

                when (usernameResult.second) {
                    1 -> {
                        usernameInputLayout.error = "Username is too short"
                    }
                    2 -> {
                        usernameInputLayout.error = "Username is empty"
                    }
                }
            } else {
                usernameInputLayout.isErrorEnabled = false
            }


            val emailInput = findViewById<TextView>(R.id.registerEmailInput)
            val emailInputLayout =
                findViewById<TextInputLayout>(R.id.registerEmailInputLayout)
            val emailResult = validateEmail(emailInput.text.toString())

            if (!emailResult.first) {
                emailInputLayout.isErrorEnabled = true

                ok = false

                when (emailResult.second) {
                    1 -> {
                        emailInputLayout.error = "Email is empty"
                    }
                    2 -> {
                        emailInputLayout.error = "Invalid email format"
                    }
                }
            } else {
                emailInputLayout.isErrorEnabled = false
            }


            val passwordInput = findViewById<TextView>(R.id.registerPasswordInput)
            val passwordInputLayout =
                findViewById<TextInputLayout>(R.id.registerPasswordInputLayout)
            val passwordResult = validatePassword(passwordInput.text.toString())

            if (!passwordResult.first) {
                passwordInputLayout.isErrorEnabled = true

                ok = false

                when (passwordResult.second) {
                    1 -> {
                        passwordInputLayout.error = "Password is too short"
                    }
                    2 -> {
                        passwordInputLayout.error = "Password is empty"
                    }
                }
            } else {
                passwordInputLayout.isErrorEnabled = false
            }


            val confirmPasswordInput = findViewById<TextView>(R.id.registerConfirmPasswordInput)
            val confirmPasswordInputLayout =
                findViewById<TextInputLayout>(R.id.registerConfirmPasswordInputLayout)
            val confirmPasswordResult = validateConfirmPassword(
                passwordInput.text.toString(),
                confirmPasswordInput.text.toString()
            )

            if (!confirmPasswordResult.first) {
                confirmPasswordInputLayout.isErrorEnabled = true

                ok = false

                when (confirmPasswordResult.second) {
                    1 -> {
                        confirmPasswordInputLayout.error = "Confirm password is too short"
                    }
                    2 -> {
                        confirmPasswordInputLayout.error = "Confirm password is empty"
                    }
                    3 -> {
                        confirmPasswordInputLayout.error = "Passwords do not match"
                    }
                }
            } else {
                confirmPasswordInputLayout.isErrorEnabled = false
            }


            if (ok) {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)

                finish()
            }
        }
    }

    private fun validateName(name: String): Pair<Boolean, Int> {
        if (name.length in 1..2) {
            return Pair(false, 1)
        }

        if (name.isEmpty()) {
            return Pair(false, 2)
        }

//        val regex = "^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}\$".toRegex()
//
//        if (!regex.matches(name)) {
//            return Pair(false, 3)
//        }

        return Pair(true, 0)
    }

    private fun validateUsername(username: String): Pair<Boolean, Int> {
        if (username.length in 1..2) {
            return Pair(false, 1)
        }

        if (username.isEmpty()) {
            return Pair(false, 2)
        }

        return Pair(true, 0)
    }

    private fun validateEmail(email: String): Pair<Boolean, Int> {
        if (email.isEmpty()) {
            return Pair(false, 2)
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Pair(false, 1)
        }

        return Pair(true, 0)
    }

    private fun validatePassword(password: String): Pair<Boolean, Int> {
        if (password.length in 1..2) {
            return Pair(false, 1)
        }

        if (password.isEmpty()) {
            return Pair(false, 2)
        }

        return Pair(true, 0)
    }

    private fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): Pair<Boolean, Int> {
        if (confirmPassword.length in 1..2) {
            return Pair(false, 1)
        }

        if (confirmPassword.isEmpty()) {
            return Pair(false, 2)
        }

        if (password != confirmPassword) {
            return Pair(false, 3)
        }

        return Pair(true, 0)
    }
}