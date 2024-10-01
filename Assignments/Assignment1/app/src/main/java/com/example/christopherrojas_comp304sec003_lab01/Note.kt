package com.example.christopherrojas_comp304sec003_lab01

data class Note(
    val id: Int,
    val title: String,
    val content: String
) : java.io.Serializable  // Serializable so we can pass com.example.christopherrojas_comp304sec003_lab01.Note objects between activities
