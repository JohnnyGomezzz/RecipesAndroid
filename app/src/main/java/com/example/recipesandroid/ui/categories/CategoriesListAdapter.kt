package com.example.recipesandroid.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesandroid.databinding.ItemCategoryBinding
import com.example.recipesandroid.model.Category

interface OnItemClickListener {

    fun onItemClick(id: Int)

}

class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val layoutCategory: LinearLayout = binding.layoutCategory
        val imageView: ImageView = binding.imCategory
        val titleTextView: TextView = binding.tvTitle
        val titleDescriptionView: TextView = binding.tvDescription
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCategoryBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
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

        viewHolder.layoutCategory.setOnClickListener {
            itemClickListener?.onItemClick(category.id)
        }
    }

    override fun getItemCount() = dataSet.size

}