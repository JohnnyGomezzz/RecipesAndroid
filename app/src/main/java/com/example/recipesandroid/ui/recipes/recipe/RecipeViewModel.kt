package com.example.recipesandroid.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RecipeUiState(
    val title: String = "",
    val numOfPortions: Int = 1,
    val ingredients: List<Ingredient> = listOf(),
    val method: List<String> = listOf(),
    val isFavorite: Boolean = false,
)

class RecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

}