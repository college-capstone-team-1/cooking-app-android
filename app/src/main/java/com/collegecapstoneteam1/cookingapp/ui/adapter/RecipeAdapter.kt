package com.collegecapstoneteam1.cookingapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.ItemRecipePreviewBinding


class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            ItemRecipePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = currentList[position]
        holder.bind(recipe)
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, recipe: Recipe, pos: Int)
    }
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class RecipeViewHolder(
        private val binding: ItemRecipePreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            itemView.apply {
                Glide.with(this)
                    .load(recipe.aTTFILENOMK)
                    .into(binding.recipeImage)
                //binding.recipeImage.load(recipe.aTTFILENOMK)
                binding.recipeTitle.text = recipe.rCPNM
                binding.recipeCategory.text = recipe.rCPPAT2
            }

            val pos = absoluteAdapterPosition
            itemView.setOnClickListener {
                Log.d("TAG", "bind: $pos")
            }
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, recipe, pos)
                }
            }
        }
    }

    companion object {
        private val BookDiffCallback = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.rCPSEQ == newItem.rCPSEQ
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }
}