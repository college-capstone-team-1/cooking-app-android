package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.FragmentHomeBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipePagerAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeVerticalAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var recipeAdapter: RecipePagerAdapter

    private lateinit var recipeVerticalAdapter1: RecipeVerticalAdapter
    private lateinit var recipeVerticalAdapter2: RecipeVerticalAdapter
    private lateinit var recipeVerticalAdapter3: RecipeVerticalAdapter
    private lateinit var recipeVerticalAdapter4: RecipeVerticalAdapter
    private lateinit var recipeVerticalAdapter5: RecipeVerticalAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel


        val list = (activity as MainActivity).list

        viewModel.getFavoriteBest()

        binding.tvList1.text = "${getStrToText(list[0])}로 만든 음식"
        binding.tvList2.text = "${getStrToText(list[1])}로 만든 음식"
        binding.tvList3.text = "${getStrToText(list[2])}로 만든 음식"
        binding.tvList4.text = "${getStrToText(list[3])}로 만든 음식"
        binding.tvList5.text = "${getStrToText(list[4])}로 만든 음식"

        viewModel.searchRecipes1(getStr(list[0]))
        viewModel.searchRecipes2(getStr(list[1]))
        viewModel.searchRecipes3(getStr(list[2]))
        viewModel.searchRecipes4(getStr(list[3]))
        viewModel.searchRecipes5(getStr(list[4]))

        setupRecyclerView()

        viewModel.searchResult.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            recipeAdapter.submitList(recipes)
        }

        viewModel.searchResult1.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            recipeVerticalAdapter1.submitList(recipes)
        }

        viewModel.searchResult2.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            recipeVerticalAdapter2.submitList(recipes)
        }

        viewModel.searchResult3.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            recipeVerticalAdapter3.submitList(recipes)
        }

        viewModel.searchResult4.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            recipeVerticalAdapter4.submitList(recipes)
        }

        viewModel.searchResult5.observe(viewLifecycleOwner) { response ->
            val recipes = response.recipes
            recipeVerticalAdapter5.submitList(recipes)
        }
    }

    private fun getStr(num: Int) = when (num) {
        1 -> "김치"
        2 -> "밥"
        3 -> "계란"
        4 -> "소고기"
        5 -> "참치"
        6 -> "돼지고기"
        7 -> "닭고기"
        8 -> "햄"
        9 -> "토마토"
        10 -> "버섯"
        11 -> "오징어"
        12 -> "고추장"
        13 -> "라면"
        14 -> "된장"
        15 -> "오이"
        16 -> "치즈"
        17 -> "감자"
        18 -> "두부"
        19 -> "호박"
        else -> "당근"
    }

    private fun getStrToText(num: Int) = when (num) {
        1 -> "김치"
        2 -> "밥으"
        3 -> "계란으"
        4 -> "소고기"
        5 -> "참치"
        6 -> "돼지고기"
        7 -> "닭고기"
        8 -> "햄으"
        9 -> "토마토"
        10 -> "버섯으"
        11 -> "오징어"
        12 -> "고추장으"
        13 -> "라면으"
        14 -> "된장으"
        15 -> "오이"
        16 -> "치즈"
        17 -> "감자"
        18 -> "두부"
        19 -> "호박으"
        else -> "당근으"
    }

    private fun setupRecyclerView() {
        //ViewPager
        recipeAdapter = RecipePagerAdapter()
        binding.viewPager.apply {
            recipeAdapter.setOnItemClickListener(object : RecipePagerAdapter.OnItemClickListener {
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeAdapter
        }

        recipeVerticalAdapter1 = RecipeVerticalAdapter()
        binding.rvList1.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recipeVerticalAdapter1.setOnItemClickListener(object : RecipeVerticalAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeVerticalAdapter1
        }


        recipeVerticalAdapter2 = RecipeVerticalAdapter()
        binding.rvList2.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recipeVerticalAdapter2.setOnItemClickListener(object : RecipeVerticalAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeVerticalAdapter2
        }

        recipeVerticalAdapter3 = RecipeVerticalAdapter()
        binding.rvList3.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recipeVerticalAdapter3.setOnItemClickListener(object : RecipeVerticalAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeVerticalAdapter3
        }
        recipeVerticalAdapter4 = RecipeVerticalAdapter()
        binding.rvList4.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recipeVerticalAdapter4.setOnItemClickListener(object : RecipeVerticalAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeVerticalAdapter4
        }
        recipeVerticalAdapter5 = RecipeVerticalAdapter()
        binding.rvList5.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recipeVerticalAdapter5.setOnItemClickListener(object : RecipeVerticalAdapter.OnItemClickListener{
                override fun onItemClick(v: View, recipe: Recipe, pos: Int) {
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentDetail(recipe)
                    findNavController().navigate(action)
                }
            })
            adapter = recipeVerticalAdapter5
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