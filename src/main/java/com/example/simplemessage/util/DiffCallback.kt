package com.example.simplemessage.util

import androidx.recyclerview.widget.DiffUtil
import com.example.simplemessage.data.models.Post

class DiffCallback(val oldList: List<Post>, val newList: List<Post>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id_db == newList[newItemPosition].id_db
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].description == newList[newItemPosition].description
    }
}