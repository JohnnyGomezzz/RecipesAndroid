package com.example.recipesandroid.ui.recipes.recipe

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesandroid.ui.Constants.ARG_RECIPE
import com.example.recipesandroid.ui.Constants.FAVORITES_PREFS_FILE_KEY
import com.example.recipesandroid.ui.Constants.FAVORITE_RECIPES_KEY
import com.example.recipesandroid.databinding.FragmentRecipeBinding
import com.example.recipesandroid.model.Recipe
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

    private val sharedPref: SharedPreferences by lazy {
        context?.getSharedPreferences(FAVORITES_PREFS_FILE_KEY, Context.MODE_PRIVATE)
            ?: throw IllegalStateException(
                "Fragment $this not attached to a context."
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            requireArguments().getParcelable(ARG_RECIPE)
        }

        initUI(view, recipe)
        initIngredientsRecycler(recipe)
        initMethodRecycler(recipe)
    }

    private fun initUI(view: View, recipe: Recipe?) {
        val drawable =
            try {
                Drawable.createFromStream(recipe?.imageUrl?.let {
                    view.context.assets.open(
                        it
                    )
                }, null)
            } catch (e: Exception) {
                Log.d("!!!", "Image not found: ${recipe?.imageUrl}")
                null
            }
        val layoutManager = LinearLayoutManager(view.context)
        val dividerItemDecoration = MaterialDividerItemDecoration(
            view.context,
            layoutManager.orientation
        )
        val favoriteRecipesIds = getFavorites()

        dividerItemDecoration.apply {
            dividerInsetEnd = 20
            dividerInsetStart = 20
            isLastItemDecorated = false
            dividerColor = 0xfff5f5f5.toInt()
            dividerThickness = 1
        }

        with(binding) {
            imageRecipe.setImageDrawable(drawable)
            tvRecipe.text = recipe?.title
            imageRecipe.contentDescription = recipe?.title

            rvIngredients.addItemDecoration(dividerItemDecoration)
            rvMethod.addItemDecoration(dividerItemDecoration)

            if (favoriteRecipesIds.contains(recipe?.id.toString())) {
                ibIsNotFavorite.visibility = View.INVISIBLE
                ibIsFavorite.visibility = View.VISIBLE
            } else {
                ibIsNotFavorite.visibility = View.VISIBLE
                ibIsFavorite.visibility = View.INVISIBLE
            }
            ibIsNotFavorite.setOnClickListener {
                ibIsNotFavorite.visibility = View.INVISIBLE
                ibIsFavorite.visibility = View.VISIBLE
                favoriteRecipesIds.add(recipe?.id.toString())
                saveFavorites(favoriteRecipesIds)
            }
            ibIsFavorite.setOnClickListener {
                ibIsNotFavorite.visibility = View.VISIBLE
                ibIsFavorite.visibility = View.INVISIBLE
                favoriteRecipesIds.remove(recipe?.id.toString())
                saveFavorites(favoriteRecipesIds)
            }
        }
    }

    private fun initIngredientsRecycler(recipe: Recipe?) {
        val ingredientsAdapter = recipe?.let { IngredientsAdapter(it.ingredients) }
        binding.rvIngredients.adapter = ingredientsAdapter

        binding.sbPortions.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    ingredientsAdapter?.updateIngredients(progress)
                    binding.tvQuantityOfPortions.text = "$progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            }
        )
    }

    private fun initMethodRecycler(recipe: Recipe?) {
        val methodAdapter = recipe?.let { MethodAdapter(it.method) }
        binding.rvMethod.adapter = methodAdapter
    }

    private fun saveFavorites(recipesIds: MutableSet<String>) {
        with(sharedPref.edit()) {
            putStringSet(FAVORITE_RECIPES_KEY, recipesIds)
            apply()
        }
    }

    private fun getFavorites(): HashSet<String> {
        return sharedPref.getStringSet(FAVORITE_RECIPES_KEY, null)?.let { HashSet(it) }
            ?: hashSetOf()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}