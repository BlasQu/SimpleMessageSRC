package com.example.simplemessage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [], exportSchema = false)
abstract class MessagesDatabase: RoomDatabase() {
    abstract fun getDao(): MessagesDao
}