package com.example.recipesandroid.ui.categories

data class Category(
    val id: Int,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
)
