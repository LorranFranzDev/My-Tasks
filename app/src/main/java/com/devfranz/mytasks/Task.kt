package com.devfranz.mytasks

data class Task(
    val title : String,
    val description : String,
    var done : Boolean = false
): java.io.Serializable
