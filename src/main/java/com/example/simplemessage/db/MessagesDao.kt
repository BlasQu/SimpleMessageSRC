package com.example.simplemessage.db

import androidx.room.*
import com.example.simplemessage.data.models.Message
import com.example.simplemessage.data.models.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Query("SELECT * FROM messages_table")
    fun getPosts(): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPosts(list: List<Post>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: Post): Long

    @Query("DELETE FROM messages_table")
    suspend fun deletePosts()

    @Delete
    suspend fun deletePost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

}