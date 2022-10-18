package com.collegecapstoneteam1.cookingapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.collegecapstoneteam1.cookingapp.data.model.Detail
import com.collegecapstoneteam1.cookingapp.databinding.ItemRecipeDetailBinding


class RecipeDetailAdapter :  ListAdapter<Detail, RecipeDetailAdapter.DetailViewHolder>(DetailDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemRecipeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val detail = getItem(position)
        holder.bind(detail!!)
    }

    inner class DetailViewHolder(
        private val binding: ItemRecipeDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: Detail) {
            with(binding){
                detailItem = detail
            }

        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("detailImage")
        fun loadDetailImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }

        private val DetailDiffCallback = object : DiffUtil.ItemCallback<Detail>() {
            override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Detail, newItem:Detail): Boolean {
                return oldItem == newItem
            }
        }
    }
}