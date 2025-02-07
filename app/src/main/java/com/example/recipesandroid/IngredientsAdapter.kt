package com.example.recipesandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroid.databinding.ItemIngredientBinding

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

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

        viewHolder.ingredientDescription.text = ingredient.description
        viewHolder.ingredientQuantityAndUnits.text = String.format(
            "%s %s",
            ingredient.quantity,
            ingredient.unitOfMeasure,
        )
    }

    override fun getItemCount() = dataSet.size

}