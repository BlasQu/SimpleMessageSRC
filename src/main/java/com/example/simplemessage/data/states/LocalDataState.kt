package com.example.simplemessage.data.states

import com.example.simplemessage.data.models.Post

sealed class LocalDataState {
    class Success(val list: List<Post>): LocalDataState()
    class Error(val error: String): LocalDataState()
    object Empty: LocalDataState()
}