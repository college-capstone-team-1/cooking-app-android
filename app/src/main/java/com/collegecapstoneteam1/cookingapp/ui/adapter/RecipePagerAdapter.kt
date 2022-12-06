package com.collegecapstoneteam1.cookingapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.ItemVerticalRecipePreviewBinding

class RecipePagerAdapter : ListAdapter<Recipe, RecipePagerAdapter.RecipeViewHolder>(RecipeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            ItemVerticalRecipePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe!!)
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, recipe: Recipe, pos: Int)
    }
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class RecipeViewHolder(
        private val binding:  ItemVerticalRecipePreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding){
                cookingItem = recipe
            }

            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d(TAG, "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, recipe, pos)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("recipeImage")
        fun loadCookingImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }

        private const val TAG = "RecipePagerAdapter"

        private val RecipeDiffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.rcpSeq == newItem.rcpSeq
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }
}