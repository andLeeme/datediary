//package com.project.datediary.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.project.datediary.databinding.ItemChatMessageBinding
//import com.project.datediary.model.ChatMessage
//import java.text.SimpleDateFormat
//import java.util.*
//
//class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
//
//    private val chatMessages = mutableListOf<ChatMessage>()
//
//    inner class ChatViewHolder(private val binding: ItemChatMessageBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(chatMessage: ChatMessage) {
//            binding.textSender.text = chatMessage.sender
//            binding.textMessage.text = chatMessage.message
//
//            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(chatMessage.timestamp)
//            binding.textTimestamp.text = timestamp
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemChatMessageBinding.inflate(inflater, parent, false)
//        return ChatViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
//        val chatMessage = chatMessages[position]
//        holder.bind(chatMessage)
//    }
//
//    override fun getItemCount(): Int {
//        return chatMessages.size
//    }
//
//    fun addMessage(chatMessage: ChatMessage) {
//        chatMessages.add(chatMessage)
//        notifyItemInserted(chatMessages.size - 1)
//    }
//}
