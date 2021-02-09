package com.example.simplemessage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemessage.data.models.Message
import com.example.simplemessage.data.models.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Query("SELECT * FROM messages_table")
    fun getPosts(): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPosts(list: List<Post>)

    @Query("DELETE FROM messages_table")
    suspend fun deletePosts()

}