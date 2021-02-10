package com.example.simplemessage.feature.messageslist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemessage.R
import com.example.simplemessage.data.states.LocalDataState
import com.example.simplemessage.databinding.FragmentMessagesListBinding
import com.example.simplemessage.feature.adapters.MessagesListAdapter
import com.example.simplemessage.feature.messages.MessagesActivity
import com.example.simplemessage.feature.messages.MessagesViewModel
import com.example.simplemessage.feature.post.PostFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class MessagesListFragment: Fragment(R.layout.fragment_messages_list) {
    lateinit var binding: FragmentMessagesListBinding
    lateinit var messagesActivity: MessagesActivity
    private val viewmodel by sharedViewModel<MessagesViewModel>()
    private val rvadapter = get<MessagesListAdapter> { parametersOf( { performClick() }) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMessagesListBinding.bind(view)
        messagesActivity = activity as MessagesActivity

        setupRecyclerView()
    }

    private fun performClick() {
        messagesActivity.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, PostFragment())
            addToBackStack("PostList")
            commit()
        }
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
                    LocalDataState.Empty -> Log.d("Debug", "Empty")
                }
            }
        }
    }
}