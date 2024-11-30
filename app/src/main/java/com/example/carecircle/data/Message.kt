//Message.kt - defines Message class - Esther

package com.example.carecircle.data

data class Message(
    val messageID: String, // unique identifier for the message
    val content: String, // content of the message
    val isFlagged: Boolean, // whether the message is flagged or not
    val date: String, // date and time of the message in format "yyyy-mm-dd hh:mm:ss"
    val conversationID: String, // ID of the conversation this Message is within
    val senderID: String // ID of the sender (i.e. phone number, must match phone numbers in "participants" for the connected conversation
)
