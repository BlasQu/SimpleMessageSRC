package com.example.simplemessage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplemessage.data.models.Post

@Database(version = 1, entities = [Post::class], exportSchema = false)
abstract class MessagesDatabase: RoomDatabase() {
    abstract fun getDao(): MessagesDao
}