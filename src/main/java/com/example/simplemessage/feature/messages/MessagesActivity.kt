package com.example.simplemessage.feature.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.simplemessage.R
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.databinding.ActivityMessagesBinding
import com.example.simplemessage.feature.messageslist.MessagesListFragment
import com.example.simplemessage.feature.post.PostFragment
import com.google.android.material.snackbar.Snackbar
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
        setupButtons()
    }

    private fun setupButtons() {
        binding.fabRefreshData.setOnClickListener {
            if (getLastFragment() == "MessagesList") viewmodel.getData()
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.holderToolbar.toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.apply {
            title = resources.getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
        }

        binding.holderToolbar.apply {
            imgAddPost.setOnClickListener {
                if (textAddTitle.visibility == View.GONE) {
                    textAddTitle.visibility = View.VISIBLE
                    imgBack.visibility = View.VISIBLE
                    supportActionBar!!.title = ""
                }
                else if (textAddTitle.text.trim().toString().isEmpty() ||
                        textAddTitle.text.trim().length < 3) textAddTitle.error = resources.getString(R.string.title_error)
                else {
                    val post = Post(0, 0, textAddTitle.text.toString(), "", "")
                    viewmodel.insertPost(post)
                    changeFragment()

                    textAddTitle.text.clear()
                    textAddTitle.visibility = View.GONE
                    imgBack.visibility = View.GONE
                    layoutAddItem.visibility = View.VISIBLE
                    supportActionBar!!.title = resources.getString(R.string.message_info)
                    imgAddPost.visibility = View.GONE
                }
            }

            imgBack.setOnClickListener {
                textAddTitle.text.clear()
                textAddTitle.visibility = View.GONE
                imgBack.visibility = View.GONE
                layoutAddItem.visibility = View.VISIBLE
                supportActionBar!!.title = resources.getString(R.string.app_name)
            }
        }
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, MessagesListFragment())
            addToBackStack("MessagesList")
            commit()
        }
    }

    fun changeFragment() {
        when (getLastFragment()) {
            "MessagesList" -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, PostFragment())
                    addToBackStack("Post")
                    commit()
                }

                binding.holderToolbar.apply {
                    layoutAddItem.visibility = View.GONE
                    textAddTitle.visibility = View.GONE
                }

                supportActionBar!!.apply {
                    setDisplayHomeAsUpEnabled(true)
                    title = resources.getString(R.string.message_info)
                }

                binding.fabRefreshData.hide()
            }
            "Post" -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, MessagesListFragment())
                    addToBackStack("MessagesList")
                    commit()
                }

                binding.holderToolbar.apply {
                    layoutAddItem.visibility = View.VISIBLE
                    imgAddPost.visibility = View.VISIBLE
                }

                supportActionBar!!.apply {
                    setDisplayHomeAsUpEnabled(false)
                    title = resources.getString(R.string.app_name)
                }

                binding.fabRefreshData.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when (getLastFragment()) {
            "MessagesList" -> finish()
            "Post" -> changeFragment()
        }
    }

    private fun getLastFragment(): String {
        val entries = supportFragmentManager.backStackEntryCount
        val lastEntry = supportFragmentManager.getBackStackEntryAt(entries - 1)
        return lastEntry.name!!
    }

    fun snackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}