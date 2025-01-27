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

class LogIn : AppCompatActivity() {
    private lateinit var emailEditText : EditText
    private lateinit var passwordEditText : EditText
    private lateinit var rememberPasswordCheckBox: CheckBox

    private val supabaseManager = SupabaseManager()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        rememberPasswordCheckBox = findViewById(R.id.сheckBox2)

        findViewById<Button>(R.id.buttonLogIn).setOnClickListener { logIn() }
        findViewById<TextView>(R.id.textViewSForgotPassword).setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        findViewById<TextView>(R.id.textViewSignInPrompt).setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        // Загрузка сохраненного пароля (если есть)
        loadSavedCredentials()
    }

    private fun logIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                supabaseManager.signIn(email, password)
                withContext(Dispatchers.Main) {
                    if (rememberPasswordCheckBox.isChecked) {
                        saveCredentials(email, password) // Сохранение пароля при необходимости
                    }
                  //  startActivity(Intent(this@LogIn, HomeActivity::class.java))
                    finish() // Закрыть текущую активность после успешной авторизации
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LogIn, e.message ?: "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadSavedCredentials() {
        // Здесь вы можете загрузить сохраненные учетные данные (например, из SharedPreferences)
        // Пример:
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")

        emailEditText.setText(savedEmail)
        passwordEditText.setText(savedPassword)
    }

    private fun saveCredentials(email: String, password: String) {
        // Сохранение учетных данных в SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }
}