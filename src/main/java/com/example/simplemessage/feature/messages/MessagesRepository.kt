package com.example.simplemessage.feature.messages

import com.example.simplemessage.data.apis.ApiService
import com.example.simplemessage.data.models.Message
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.db.MessagesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MessagesRepository(
    val dao: MessagesDao,
    val api: ApiService
) {
    fun getPosts(): Flow<List<Post>> = dao.getPosts()
    suspend fun insertPosts(list: List<Post>) = dao.insertPosts(list)

    suspend fun responseGetPosts(): List<Post>? {
        val response = api.getApiPosts()

        if (response.isSuccessful) {
            val body = response.body()
            return body!!.posts
        }
        return null
    }

    suspend fun deletePost(post: Post) = dao.deletePost(post)

    suspend fun updatePost(post: Post) = dao.updatePost(post)

    suspend fun insertPost(post: Post): Long = dao.insertPost(post)

}