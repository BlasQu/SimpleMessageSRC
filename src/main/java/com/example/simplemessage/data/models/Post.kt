package com.example.simplemessage.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "messages_table")
data class Post(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_db") var id_db: Int = 0,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "icon") var icon: String
)

// 2 typy id, jedno dla database, drugie dla id posta pobranego z api, potrzebne,
// aby rozróżnić posta lokalnego od api (można także użyć to do późniejszych czynności)