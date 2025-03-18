package com.example.recipesandroid.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesandroid.ui.Constants.ARG_CATEGORY_ID
import com.example.recipesandroid.ui.Constants.ARG_CATEGORY_IMAGE_URL
import com.example.recipesandroid.ui.Constants.ARG_CATEGORY_NAME
import com.example.recipesandroid.R
import com.example.recipesandroid.data.STUB
import com.example.recipesandroid.databinding.FragmentCategoriesListBinding
import com.example.recipesandroid.ui.recipes.recipeslist.RecipesListFragment

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentCategoriesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentCategoriesListBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(STUB.getCategories())
        binding.rvCategories.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(
            object : OnItemClickListener {
                override fun onItemClick(id: Int) {
                    openRecipesByCategoryId(id)
                }
            }
        )
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val categoryName = STUB.getCategories().filter { it.id == categoryId }[0].title
        val categoryImageUrl = STUB.getCategories().filter { it.id == categoryId }[0].imageUrl

        val bundle = (categoryImageUrl)?.let {
            bundleOf(
                ARG_CATEGORY_ID to categoryId,
                ARG_CATEGORY_NAME to categoryName,
                ARG_CATEGORY_IMAGE_URL to it,
            )
        }
        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}