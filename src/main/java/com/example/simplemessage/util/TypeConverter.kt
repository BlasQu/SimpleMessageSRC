package com.example.simplemessage.util

import androidx.room.TypeConverter
import com.example.simplemessage.data.models.Post
import com.google.gson.Gson

class TypeConverter {

    @TypeConverter
    fun fromListToJSON(list: List<Post>): String =
        Gson().toJson(list)

    @TypeConverter
    fun fromJSONToList(json: String): List<Post> =
        Gson().fromJson(json, Array<Post>::class.java).toList()
}