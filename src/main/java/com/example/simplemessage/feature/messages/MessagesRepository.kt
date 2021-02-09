package com.example.simplemessage.feature.messages

import com.example.simplemessage.data.models.Message
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.db.MessagesDao
import kotlinx.coroutines.flow.Flow

class MessagesRepository(
    val dao: MessagesDao
) {

    fun getPosts(): Flow<List<Post>> = dao.getPosts()

}