package com.munidigital.bc2201.challenge2

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.munidigital.bc2201.challenge2.databinding.ActivityItemBinding

class ChatAdapter(private val context: Context): ListAdapter<ChatMessage, ChatAdapter.ViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        val binding = ActivityItemBinding.inflate(LayoutInflater
            .from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        val chatMessage = getItem(position)
        holder.bind(chatMessage)
    }

    inner class ViewHolder(private val binding: ActivityItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            val chatListItemMessage = binding.chatListItemMessage
            if (chatMessage.isQuestion) {
                chatListItemMessage.gravity = Gravity.END
                chatListItemMessage.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            } else {
                chatListItemMessage.gravity = Gravity.START
                chatListItemMessage.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            }
            chatListItemMessage.text = chatMessage.message
            binding.executePendingBindings()
        }
    }
}