package com.example.recipesandroid

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroid.databinding.FragmentCategoriesListBinding
import com.example.recipesandroid.databinding.ItemCategoryBinding

class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    private var _binding: ItemCategoryBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ItemCategoryBinding must not be null")


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imCategory)
        val titleTextView: TextView = view.findViewById(R.id.tvTitle)
        val titleDescriptionView: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        _binding = ItemCategoryBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category: Category = dataSet[position]

        viewHolder.titleTextView.text = category.title
        viewHolder.titleDescriptionView.text = category.description

        val drawable =
            try {
                Drawable.createFromStream(category.imageUrl?.let {
                    viewHolder.itemView.context.assets.open(
                        it
                    )
                }, null)
            } catch (e: Exception) {
                Log.d("!!!", "Image not found: ${category.imageUrl}")
                null
            }
        viewHolder.imageView.setImageDrawable(drawable)
        viewHolder.imageView.setContentDescription(category.title)
    }

    override fun getItemCount() = dataSet.size

}