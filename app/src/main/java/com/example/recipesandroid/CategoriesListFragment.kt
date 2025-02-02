package com.example.recipesandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesandroid.databinding.FragmentCategoriesListBinding

const val ARG_CATEGORY_ID = 0
const val ARG_CATEGORY_NAME = 1
const val ARG_CATEGORY_IMAGE_URL = 2

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
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(categoryId)
                }
            }
        )
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val categoryName = STUB.getCategories().filter { it.id == categoryId }[0].title
        val categoryImageUrl = STUB.getCategories().filter { it.id == categoryId }[0].imageUrl

        parentFragmentManager.commit {
            val bundle = (categoryImageUrl)?.let {
                bundleOf(
                    categoryId.toString() to ARG_CATEGORY_ID,
                    categoryName to ARG_CATEGORY_NAME,
                    it to ARG_CATEGORY_IMAGE_URL,
                )
            }
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}