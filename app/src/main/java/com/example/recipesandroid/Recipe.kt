package com.example.recipesandroid

data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: MutableList<Ingredient>,
    val method: MutableList<String>,
    val imageUrl: String? = null,
)
