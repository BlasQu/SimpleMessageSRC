package com.example.simplemessage.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.simplemessage.R
import com.example.simplemessage.databinding.ActivitySplashBinding
import com.example.simplemessage.feature.messages.MessagesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textWelcome.animate().apply {
            duration = 1000
            alpha(1f)
        }

        val intent = Intent(this, MessagesActivity::class.java)
        lifecycleScope.launch {
            delay(5000)
            startActivity(intent)
            finish()
        }
    }
}