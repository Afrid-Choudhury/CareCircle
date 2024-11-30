//SmsAdapter.kt - renders the messages in RecyclerView. checks if parent by ifParent(), to obfuscate messages - Esther

package com.example.carecircle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carecircle.R
import com.example.carecircle.data.Message

class SmsAdapter(
    private val messages: List<Message>,
    private val isParent: Boolean
) : RecyclerView.Adapter<SmsAdapter.SmsViewHolder>(){

    class SmsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageContent: TextView = view.findViewById(R.id.messageContent)
        val messageDate: TextView = view.findViewById(R.id.messageDate)
        val messageSender: TextView = view.findViewById(R.id.messageSender)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_sms, parent, false)
        return SmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        val message = messages[position]
        holder.messageContent.text =
            if (isParent && !message.isFlagged) "Message hidden (not flagged)" else message.content
        holder.messageDate.text = message.date
        holder.messageSender.text = if (message.senderID == "child1") "You" else message.senderID
    }

    override fun getItemCount(): Int = messages.size
}