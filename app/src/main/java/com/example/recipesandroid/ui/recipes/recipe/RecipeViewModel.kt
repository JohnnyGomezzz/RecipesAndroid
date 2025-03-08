package com.example.recipesandroid.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipesandroid.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val numOfPortions: Int = 1,
        val isFavorite: Boolean = false,
    )

}