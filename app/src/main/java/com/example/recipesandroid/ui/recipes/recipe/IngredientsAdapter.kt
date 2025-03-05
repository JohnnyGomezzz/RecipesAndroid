package com.example.recipesandroid.ui.recipes.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroid.databinding.ItemIngredientBinding
import com.example.recipesandroid.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

        private var quantityOfPortions = 1

    class ViewHolder(binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        val ingredientDescription: TextView = binding.tvIngredientDescription
        val ingredientQuantityAndUnits: TextView = binding.tvQuantityAndUnits
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemIngredientBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient: Ingredient = dataSet[position]
        val totalQuantity = BigDecimal(ingredient.quantity) * BigDecimal(quantityOfPortions)
        val displayQuantity = totalQuantity
            .setScale(1, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()

        viewHolder.ingredientDescription.text = ingredient.description
        viewHolder.ingredientQuantityAndUnits.text = String.format(
            "%s %s",
            displayQuantity,
            ingredient.unitOfMeasure,
        )
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(progress: Int) {
        quantityOfPortions = progress
        notifyDataSetChanged()
    }

}