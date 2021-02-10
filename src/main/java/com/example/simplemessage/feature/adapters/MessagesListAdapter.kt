package com.example.simplemessage.feature.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.databinding.RvMessageItemBinding
import com.example.simplemessage.util.DiffCallback

class MessagesListAdapter: RecyclerView.Adapter<MessagesListAdapter.ViewHolder>() {
    private val messagesData = mutableListOf<Post>()

    inner class ViewHolder(val binding: RvMessageItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = messagesData[position].title
        val iconUri = messagesData[position].icon

        holder.binding.apply {
            textTitle.text = title
            imgIcon.setImageURI(iconUri)
        }
    }

    override fun getItemCount(): Int {
        return messagesData.size
    }

    fun submitData(newList: List<Post>) {
        val result = DiffUtil.calculateDiff(DiffCallback(messagesData, newList))
        messagesData.apply {
            clear()
            addAll(newList)
        }
        result.dispatchUpdatesTo(this)
    }
}