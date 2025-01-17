package com.example.project2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val screen = findViewById<ImageView>(R.id.imageZ)
        screen.alpha = 0f
        screen.animate().setDuration(1000).alpha(1f).withEndAction {
            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val isOnboarded = sharedPreferences.getBoolean("is_onboarded", false)

            if (isOnboarded) {

                startActivity(Intent(this, Onboarding::class.java))
                finish()
            } else {
                // Здесь можно добавить логику для главного экрана приложения
            }
        }
    }
}