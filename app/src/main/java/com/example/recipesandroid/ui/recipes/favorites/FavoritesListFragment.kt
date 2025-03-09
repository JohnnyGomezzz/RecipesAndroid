package com.example.recipesandroid.ui.recipes.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesandroid.ui.Constants.ARG_RECIPE
import com.example.recipesandroid.ui.Constants.FAVORITES_PREFS_FILE_KEY
import com.example.recipesandroid.ui.Constants.FAVORITE_RECIPES_KEY
import com.example.recipesandroid.R
import com.example.recipesandroid.data.STUB
import com.example.recipesandroid.databinding.FragmentFavoritesListBinding
import com.example.recipesandroid.ui.recipes.recipe.RecipeFragment
import com.example.recipesandroid.ui.recipes.recipeslist.OnItemClickListener
import com.example.recipesandroid.ui.recipes.recipeslist.RecipesListAdapter

class FavoritesListFragment : Fragment() {

    private var _binding: FragmentFavoritesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentFavoritesListBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
    }

    private fun getFavorites(): HashSet<Int> {
        val sharedPref: SharedPreferences by lazy {
            context?.getSharedPreferences(FAVORITES_PREFS_FILE_KEY, Context.MODE_PRIVATE)
                ?: throw IllegalStateException(
                    "Fragment $this not attached to a context."
                )
        }
        val setOfString = sharedPref.getStringSet(FAVORITE_RECIPES_KEY, null)?.let { HashSet(it) }
            ?: hashSetOf()
        val setOfInt = hashSetOf<Int>()
        setOfString.forEach { setOfInt.add(it.toInt()) }
        return setOfInt
    }

    private fun initRecycler() {
        val idsSet = STUB.getRecipesByIds(getFavorites())
        val recipesAdapter = RecipesListAdapter(idsSet)
        binding.rvFavoritesList.adapter = recipesAdapter

        recipesAdapter.setOnItemClickListener(
            object : OnItemClickListener {
                override fun onItemClick(id: Int) {
                    openRecipeByRecipeId(id)
                }
            }
        )
        if (getFavorites().isNotEmpty()) {
            binding.rvFavoritesList.visibility = View.VISIBLE
            binding.tvNoFavorites.visibility = View.GONE
        }
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)

        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}