package com.example.personalfinance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import com.example.personalfinance.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            FLAG_LAYOUT_NO_LIMITS,
            FLAG_LAYOUT_NO_LIMITS
        )

        val thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()

    }
}