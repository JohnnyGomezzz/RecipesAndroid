package com.example.recipesandroid.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesandroid.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    private val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    private val _currentRecipe: MutableLiveData<Recipe> by lazy {
        MutableLiveData<Recipe>()
    }
    val currentRecipe: LiveData<Recipe> = _currentRecipe

    private val _numOfPortions: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val numOfPortions: LiveData<Int> = _numOfPortions

    private val _isFavorite: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        Log.i("!!!", "Сообщение от VM")
//        _isFavorite.value = uiState.value.isFavorite
        val isFavoriteState: LiveData<Boolean> = _uiState.value.isFavorite
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val numOfPortions: Int = 1,
        val isFavorite: Boolean = false,
    )

}