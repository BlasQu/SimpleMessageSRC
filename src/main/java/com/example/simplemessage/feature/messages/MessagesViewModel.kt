package com.example.simplemessage.feature.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.data.states.LocalDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessagesViewModel(
    val repository: MessagesRepository
): ViewModel() {

    val postsDataState = MutableStateFlow<LocalDataState>(LocalDataState.Success(emptyList<Post>()))
    val currentPost = MutableStateFlow<Post?>(null)

    init {
        viewModelScope.launch {
            repository.getPosts().collect {
                if (it.isEmpty()) {
                    postsDataState.value = LocalDataState.Empty
                    repository.responseGetPosts()?.let {
                        viewModelScope.launch {
                            repository.insertPosts(it)
                        }
                    }
                }
                else postsDataState.value = LocalDataState.Success(it)
            }
        }
    }

    fun deletePost() {
        val post = currentPost.value!!
        viewModelScope.launch {
            repository.deletePost(post)
            currentPost.value = null
        }
    }

    fun updatePost(updatedTitle: String, updatedDescription: String) {
        val post = currentPost.value!!
        post.apply {
            title = updatedTitle
            description = updatedDescription
        }
        viewModelScope.launch {
            repository.updatePost(post)
            currentPost.value = post
        }
    }

}
