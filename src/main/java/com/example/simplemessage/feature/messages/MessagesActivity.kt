package com.example.simplemessage.feature.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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
        setContentView(binding.root)

        setupFragment()
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = binding.holderToolbar.toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.apply {
            title = resources.getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, MessagesListFragment())
            addToBackStack("MessagesList")
            commit()
        }
    }
}