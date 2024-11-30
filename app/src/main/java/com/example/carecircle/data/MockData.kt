//mock predefined Parent, Child, Conversation, Message data for deliverable 3 - Esther

package com.example.carecircle.data

object MockData {
    val parent = Parent( // mockup Parent
        parentID = "parent1",
        name = "Parent User",
        children = listOf("child1")
    )

    val child = Child(
        childID = "child1", // mockup Child
        name = "Child User",
        conversationIDs = listOf("conv1")
    )

    val conversations = listOf( // mockup list of Conversations
        Conversation( // mockup Conversations; just 1 for the deliverable
            conversationID = "conv1",
            childID = "child1",
            participants = listOf("+111222333", "+444555666"), // participants who aren't the child
            lastMessageDate = "2024-11-22 15:30:45"
        )
    )

    val messages = listOf( // mockup list of Messages
        Message( // mockup Message
            messageID = "msg1",
            content = "This is a flagged message.",
            isFlagged = true,
            date = "2024-11-22 12:45:17",
            conversationID = "conv1",
            senderID = "child1" // sent by the child
        ),
        Message(
            messageID = "msg2",
            content = "This is a safe message.",
            isFlagged = false,
            date = "2024-11-22 15:30:45",
            conversationID = "conv1",
            senderID = "+444555666" // sent by a participant who isn't the child; must be one of the participant numbers in 'conv1'
        )
    )
}