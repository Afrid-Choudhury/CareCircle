// Parent.kt - the class definition for Parent objects - Esther

package com.example.carecircle.data

data class Parent(
    val parentID: String, //unique identifier for the parent
    val name: String, //parent's display name
    val children: List<String> //list of child IDs connected to this parent
)
