package com.example.project2

import SupabaseManager
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUp : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private val supabaseManager = SupabaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailEditText = findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword2)
        termsCheckBox = findViewById(R.id.checkBox)

        findViewById<Button>(R.id.button).setOnClickListener { signUp() }

        findViewById<TextView>(R.id.textViewSignInPrompt).setOnClickListener {
            startActivity(Intent(this, LogIn::class.java))
        }
    }

    private fun signUp() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Введите корректный email.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Пароли не совпадают.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!termsCheckBox.isChecked) {
            Toast.makeText(this, "Необходимо согласие с условиями.", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                supabaseManager.signUp(email, password, "", "")
                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@SignUp, LogIn::class.java))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SignUp, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidEmail(email:String): Boolean {
        val emailPattern = "[a-z0-9]+@[a-z0-9]+\\.ru"
        return email.matches(emailPattern.toRegex())
    }
}