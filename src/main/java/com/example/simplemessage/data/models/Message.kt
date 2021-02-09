package com.example.simplemessage.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val posts: List<Post>
)