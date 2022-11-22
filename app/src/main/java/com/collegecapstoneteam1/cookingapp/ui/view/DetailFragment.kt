package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.FragmentDetailBinding
import com.collegecapstoneteam1.cookingapp.databinding.FragmentFavoriteBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeAdapter
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeDetailAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel
import com.collegecapstoneteam1.cookingapp.util.collectLatestStateFlow
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    
    private val args by navArgs<DetailFragmentArgs>()

    private lateinit var viewModel:MainViewModel
    private lateinit var detailAdapter: RecipeDetailAdapter

    private var state = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe = args.recipe

        viewModel = (activity as MainActivity).viewModel

        binding.tvRecipeTitle.text = recipe.rcpNm
        binding.tvRecipePart.text = recipe.rcpPartsDtls

        Glide.with(view.context)
            .load(recipe.attFileNoMain)
            .into(binding.ivRecipeMainImg)

        setupRecyclerView()
        detailAdapter.submitList(recipe.getDetailList())

        collectLatestStateFlow(viewModel.favoriteRecipes){
            state = it.contains(recipe)
            binding.ivRecipeFavorite.isActivated = state
        }

        binding.ivRecipeFavorite.setOnClickListener{
            if(state) {
                viewModel.deleteRecipe(recipe)
                Snackbar.make(view, "Recipe has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveRecipe(recipe)
                    }
                }.show()
            } else{
                viewModel.saveRecipe(recipe)
                Snackbar.make(view, "Recipe has Saved", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.deleteRecipe(recipe)
                    }
                }.show()
            }
        }
    }

    private fun setupRecyclerView() {
        detailAdapter = RecipeDetailAdapter()
        binding.rvDetailResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = detailAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
    
    companion object{
        private const val TAG = "DetailFragment"
    }
}