package com.example.simplemessage.feature.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplemessage.R
import com.example.simplemessage.databinding.ActivityMessagesBinding

class MessagesActivity : AppCompatActivity() {

    lateinit var binding: ActivityMessagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_messages)
    }
}