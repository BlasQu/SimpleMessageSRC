package com.example.simplemessage.feature.post

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.simplemessage.R
import com.example.simplemessage.databinding.FragmentPostBinding
import com.example.simplemessage.feature.messages.MessagesActivity
import com.example.simplemessage.feature.messages.MessagesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*


class PostFragment: Fragment(R.layout.fragment_post) {

    lateinit var binding: FragmentPostBinding
    lateinit var messagesActivity: MessagesActivity
    private val viewmodel by sharedViewModel<MessagesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostBinding.bind(view)
        messagesActivity = activity as MessagesActivity

        setupPost()
        setupButtons()
    }

    private fun setupButtons() {
        val keyboard: InputMethodManager = messagesActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.apply {
            binding.btnDelete.setOnClickListener {
                viewmodel.deletePost()
                messagesActivity.changeFragment()
            }

            binding.btnEdit.setOnClickListener {
                if (textTitle.visibility == View.VISIBLE) {
                    inputTitle.apply {
                        setText(textTitle.text)
                        visibility = View.VISIBLE
                        requestFocus()
                    }

                    inputDescription.apply {
                        setText(textDescription.text)
                        visibility = View.VISIBLE
                    }

                    textTitle.visibility = View.GONE
                    textDescription.visibility = View.GONE
                    btnEdit.text = resources.getString(R.string.update)

                    keyboard.showSoftInput(inputTitle, InputMethodManager.SHOW_IMPLICIT)
                }
                else if (inputTitle.text.isEmpty() || inputTitle.text.trim().length < 3)
                    inputTitle.error = resources.getString(R.string.title_error)
                else {
                    textTitle.apply {
                        text = inputTitle.text.toString()
                        visibility = View.VISIBLE
                    }

                    textDescription.apply {
                        text = inputDescription.text.toString()
                        visibility = View.VISIBLE
                    }

                    inputTitle.visibility = View.GONE
                    inputDescription.visibility = View.GONE
                    btnEdit.text = resources.getString(R.string.edit)

                    viewmodel.updatePost(inputTitle.text.toString(), inputDescription.text.toString())
                }
            }
        }
    }

    private fun setupPost() {
        lifecycleScope.launch {
            viewmodel.currentPost.collect { post ->
                Log.d("debug", post.toString())
                binding.apply {
                    post?.let {
                        val title = post.title
                        val desc = post.description
                        val iconUri = post.icon

                        if (iconUri.isEmpty()) imgPicture.setActualImageResource(R.drawable.no_image_found)
                        else imgPicture.setImageURI(iconUri)
                        textTitle.text = title.capitalize(Locale.getDefault())
                        textDescription.apply {
                            text = desc.capitalize(Locale.getDefault())
                            movementMethod = ScrollingMovementMethod()
                        }
                    }
                }
            }
        }
    }
}