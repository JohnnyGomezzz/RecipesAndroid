package com.example.recipesandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroid.databinding.ItemIngredientBinding

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
        val newQuantity = ingredient.quantity.toDouble() * quantityOfPortions

        viewHolder.ingredientDescription.text = ingredient.description
        viewHolder.ingredientQuantityAndUnits.text = String.format(
            "%s %s",
            hasDot(newQuantity),
            ingredient.unitOfMeasure,
        )
    }

    private fun hasDot(quantity: Double): String {
        return if (quantity % 1.0 == 0.0) {
            quantity.toString().substringBefore(".")
        } else {
            quantity.toString()
        }
    }

    override fun getItemCount() = dataSet.size

    fun updateIngredients(progress: Int) {
        quantityOfPortions = progress
    }

}