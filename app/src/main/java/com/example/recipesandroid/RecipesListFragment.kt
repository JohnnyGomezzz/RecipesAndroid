package com.example.recipesandroid

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesandroid.Constants.ARG_CATEGORY_ID
import com.example.recipesandroid.Constants.ARG_CATEGORY_IMAGE_URL
import com.example.recipesandroid.Constants.ARG_CATEGORY_NAME
import com.example.recipesandroid.Constants.ARG_RECIPE
import com.example.recipesandroid.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipesListBinding must not be null")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
        categoryName = requireArguments().getString(ARG_CATEGORY_NAME)
        categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)

        initUI(view)
        initRecycler()
    }

    private fun initUI(view: View) {
        val drawable =
            try {
                Drawable.createFromStream(categoryImageUrl?.let {
                    view.context.assets.open(
                        it
                    )
                }, null)
            } catch (e: Exception) {
                Log.d("!!!", "Image not found: $categoryImageUrl")
                null
            }
        binding.imageRecipesList.setImageDrawable(drawable)
        binding.imageRecipesList.contentDescription = categoryName
        binding.tvRecipesList.text = categoryName
    }

    private fun initRecycler() {
        val recipesAdapter = RecipesListAdapter(STUB.getRecipesByCategoryId(0))
        binding.rvRecipesList.adapter = recipesAdapter

        recipesAdapter.setOnItemClickListener(
            object : OnItemClickListener {
                override fun onItemClick(id: Int) {
                    openRecipeByRecipeId(id)
                }
            }
        )
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