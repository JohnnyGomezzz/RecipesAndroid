package com.example.recipesandroid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    var quantity: String,
    val unitOfMeasure: String,
    val description: String,
): Parcelable
