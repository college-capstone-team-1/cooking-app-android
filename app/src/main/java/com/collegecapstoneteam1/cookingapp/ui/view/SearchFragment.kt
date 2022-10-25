package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.FragmentSearchBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        viewModel.searchResult.observe(viewLifecycleOwner) { response ->
            val recipes = response.cOOKRCP01.recipes
            recipeAdapter.submitList(recipes)
        }
        binding.btnLeftsearch.setOnClickListener {
            viewModel.decreaseNum()
        }
        binding.btnSearch.setOnClickListener {
            viewModel.searchRecipesList()
        }
        binding.btnRightsearch.setOnClickListener {
            viewModel.addNum()
        }
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            recipeAdapter.setOnItemClickListener( object :RecipeAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    Toast.makeText(context,recipe.rCPNM,Toast.LENGTH_SHORT).show()
                }
            })
            adapter = recipeAdapter

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}