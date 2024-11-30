//Child.kt - class definition for a Child object - Esther

package com.example.carecircle.data

data class Child(
    val childID: String, //unique identifier for the child
    val name: String, //child's display name
    val conversationIDs: List<String> //list of conversation IDs this child is associated with
)
