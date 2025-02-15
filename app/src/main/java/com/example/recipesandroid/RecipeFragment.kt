package com.example.recipesandroid

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
import com.example.recipesandroid.Constants.ARG_RECIPE
import com.example.recipesandroid.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration


class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")

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
        binding.imageRecipe.setImageDrawable(drawable)
        binding.tvRecipe.text = recipe?.title
        binding.imageRecipe.contentDescription = recipe?.title

        dividerItemDecoration.apply {
            dividerInsetEnd = 20
            dividerInsetStart = 20
            isLastItemDecorated = false
            dividerColor = 0xfff5f5f5.toInt()
        }
        binding.rvIngredients.addItemDecoration(dividerItemDecoration)
        binding.rvMethod.addItemDecoration(dividerItemDecoration)

        with(binding) {
            ibIsNotFavorite.setOnClickListener {
                ibIsNotFavorite.visibility = View.INVISIBLE
                ibIsFavorite.visibility = View.VISIBLE
            }
            ibIsFavorite.setOnClickListener {
                ibIsNotFavorite.visibility = View.VISIBLE
                ibIsFavorite.visibility = View.INVISIBLE
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}