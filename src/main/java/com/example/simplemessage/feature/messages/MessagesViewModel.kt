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

    init {
        viewModelScope.launch {
            repository.getPosts().collect {
                if (it.isEmpty()) postsDataState.value = LocalDataState.Empty
                else postsDataState.value = LocalDataState.Success(it)
            }
        }
    }

}