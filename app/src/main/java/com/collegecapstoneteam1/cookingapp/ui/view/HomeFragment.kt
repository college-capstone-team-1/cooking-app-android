package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.FragmentHomeBinding
import com.collegecapstoneteam1.cookingapp.databinding.FragmentSettingBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipePagerAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel

import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var recipeAdapter: RecipePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        viewModel.getFavoriteBest()

        setupRecyclerView()

        viewModel.searchResult.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            Log.d(TAG, "onViewCreated: ${recipes}")
            recipeAdapter.submitList(recipes)
        }

    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipePagerAdapter()
        binding.viewPager.apply {
            recipeAdapter.setOnItemClickListener(object : RecipePagerAdapter.OnItemClickListener {
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    //Toast.makeText(context,recipe.rCPNM,Toast.LENGTH_SHORT).show()
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeAdapter

        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}