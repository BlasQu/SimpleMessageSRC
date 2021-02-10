package com.example.simplemessage.feature.messages

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.data.states.LocalDataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessagesViewModel(
    val repository: MessagesRepository
): ViewModel() {

    val postsDataState = MutableStateFlow<LocalDataState>(LocalDataState.Success(emptyList<Post>()))
    val currentPost = MutableStateFlow<Post?>(null)

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            repository.getPosts().collect {
                if (it.isEmpty() && repository.isConnected()) {
                    insertPosts()
                }
                else if (it.isEmpty()) {
                    postsDataState.value = LocalDataState.Error("Brak połączenia internetowego!")
                }
                else postsDataState.value = LocalDataState.Success(it)
            }
        }
    }

    suspend fun insertPosts() {
        postsDataState.value = LocalDataState.Empty
        repository.responseGetPosts()?.let {
            viewModelScope.launch {
                repository.insertPosts(it)
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

    fun updatePost(updatedTitle: String, updatedDescription: String, imageUri: Uri?) {
        val post = currentPost.value!!
        post.apply {
            title = updatedTitle
            description = updatedDescription
            imageUri?.let {
                icon = imageUri.toString()
            }
        }
        currentPost.value = post
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }

    fun insertPost(post: Post) {
        viewModelScope.launch {
            val insertedID = repository.insertPost(post)
            post.id_db = insertedID.toInt()
            currentPost.value = post
        }
    }

}
