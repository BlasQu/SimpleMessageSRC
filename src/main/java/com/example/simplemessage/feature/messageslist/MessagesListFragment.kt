package com.example.simplemessage.feature.messageslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemessage.R
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.data.states.LocalDataState
import com.example.simplemessage.databinding.FragmentMessagesListBinding
import com.example.simplemessage.feature.messages.MessagesActivity
import com.example.simplemessage.feature.messages.MessagesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MessagesListFragment: Fragment(R.layout.fragment_messages_list) {
    lateinit var binding: FragmentMessagesListBinding
    lateinit var messagesActivity: MessagesActivity
    private var rvScrollState: Int?  = null
    private val viewmodel by sharedViewModel<MessagesViewModel>()
    // private val rvadapter = get<MessagesListAdapter> { parametersOf({ post: Post -> performClick(post) }) }
    // problem with passing paramsOf (lambda with post item) to adapter with Koin
    // instead of using normal injection
    private val rvadapter = MessagesListAdapter() {
        post -> performClick(post)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMessagesListBinding.bind(view)
        messagesActivity = activity as MessagesActivity

        setupRecyclerView()
    }


    private fun performClick(post: Post) {
        viewmodel.currentPost.value = post
        messagesActivity.changeFragment()
    }

    private fun setupRecyclerView() {
        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(messagesActivity)
            adapter = rvadapter
        }

        lifecycleScope.launch {
            viewmodel.postsDataState.collect {
                when (it) {
                    is LocalDataState.Success -> rvadapter.submitData(it.list)
                    is LocalDataState.Error -> {
                        rvadapter.submitData(emptyList())
                        Snackbar.make(binding.root, it.error, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}