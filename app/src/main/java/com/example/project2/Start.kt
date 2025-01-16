package com.example.project2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Start : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val screen = findViewById<ImageView>(R.id.imageView3)
        screen.alpha = 0f
        screen.animate().setDuration(1000).alpha(1f).withEndAction {
            startActivity(Intent(this,MainActivity::class.java))

            finish()
        }
    }
}