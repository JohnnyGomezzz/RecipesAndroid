package com.example.recipesandroid.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesandroid.model.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeState: MutableLiveData<RecipeState> = MutableLiveData<RecipeState>()
    val recipeState: LiveData<RecipeState> = _recipeState

    init {
        updateFavoriteStatus(true)
        Log.i("!!!", "Сообщение от VM")
    }

    private fun updateFavoriteStatus(isFavorite: Boolean) {
        _recipeState.value = recipeState.value?.copy(isFavorite = isFavorite)
    }

    data class RecipeState(
        val recipe: Recipe? = null,
        val numOfPortions: Int = 1,
        val isFavorite: Boolean = false,
    )

}