package com.example.project2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Onboarding : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button

    private val queue = mutableListOf<OnboardingItem>()
    private var currentIndex = 0


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        imageView = findViewById(R.id.imageView)
        titleTextView = findViewById(R.id.titletextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        nextButton = findViewById(R.id.nextButton)
        skipButton = findViewById(R.id.skupButton)

        initializeQueue()
        showCurrentItem()

        nextButton.setOnClickListener {
            if (currentIndex < queue.size - 1) {
                currentIndex++
                showCurrentItem()
            } else {
                val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("is_onboarded", true).apply()
                startActivity(Intent(this, SignUp::class.java))
                finish()
            }
        }

        skipButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("is_onboarded", true).apply()
            startActivity(Intent(this, EndActivity::class.java))
            finish()
        }
    }

    private fun initializeQueue() {
        queue.add(OnboardingItem(R.drawable.img_1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination"))
        queue.add(OnboardingItem(R.drawable.img_2, "Flexible Payment", "Different modes of payment either before and after delivery without stress"))
        queue.add(OnboardingItem(R.drawable.img_3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination"))
    }

    private fun showCurrentItem() {
        if (currentIndex < queue.size) {
            val currentItem = queue[currentIndex]
            imageView.setImageResource(currentItem.imageResId)
            titleTextView.text = currentItem.title
            descriptionTextView.text = currentItem.description

            imageView.alpha = 0f
            titleTextView.alpha = 0f
            descriptionTextView.alpha = 0f

            imageView.animate().alpha(1f).setDuration(500)
            titleTextView.animate().alpha(1f).setDuration(500)
            descriptionTextView.animate().alpha(1f).setDuration(500)

            if (currentIndex == queue.size - 1) {
                nextButton.text = "Sign Up"
                skipButton.visibility = View.GONE // Скрыть кнопку Skip на последнем слайде
            } else {
                nextButton.text = "Next"
                skipButton.visibility = View.VISIBLE // Показать кнопку Skip на остальных слайдах
            }
        }
    }
}

data class OnboardingItem(val imageResId: Int, val title: String, val description: String)