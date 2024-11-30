//Conversation.kt - class definition for conversations - Esther

package com.example.carecircle.data

data class Conversation(
    val conversationID: String, // unique identifier for the conversation
    val childID: String, // ID of the child the conversation 'belongs' to
    val participants: List<String>, // list of participant phone numbers
    val lastMessageDate: String // date and time of the most recent message, format "yyyy-mm-dd hh:mm:ss"
)
