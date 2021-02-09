package com.example.simplemessage.feature.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.simplemessage.R
import com.example.simplemessage.databinding.ActivityMessagesBinding
import com.example.simplemessage.feature.messageslist.MessagesListFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MessagesActivity : AppCompatActivity() {

    lateinit var binding: ActivityMessagesBinding
    private val viewmodel by viewModel<MessagesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_messages)

        setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, MessagesListFragment())
            addToBackStack("MessagesList")
            commit()
        }
    }
}