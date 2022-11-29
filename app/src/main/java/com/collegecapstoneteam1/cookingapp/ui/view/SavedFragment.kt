package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.FragmentSavedBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeRoomAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel
import com.collegecapstoneteam1.cookingapp.util.collectLatestStateFlow
import com.google.android.material.snackbar.Snackbar


class SavedFragment : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var recipeAdapter: RecipeRoomAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        setupTouchHelper(view)
        
        collectLatestStateFlow(viewModel.savedRecipes){
            recipeAdapter.submitList(it)
        }
    }
    private fun setupRecyclerView() {
        recipeAdapter = RecipeRoomAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    0
                )
            )

            recipeAdapter.setOnItemClickListener( object :RecipeRoomAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = SavedFragmentDirections.actionFragmentSavedToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeAdapter

        }
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val recipe = recipeAdapter.currentList[position]
                viewModel.deleteRecipe(recipe)
                Snackbar.make(view, "Recipe has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveRecipe(recipe)
                    }
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSearchResult)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        private const val TAG = "SavedFragment"
    }
}