package com.example.recipesandroid

data class Category(
    val id: Int,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
)
