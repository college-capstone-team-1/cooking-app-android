package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.model.Recipe
import com.collegecapstoneteam1.cookingapp.databinding.FragmentSearchBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel
import com.collegecapstoneteam1.cookingapp.util.Constants.SEARCH_COOKS_TIME_DELAY
import com.collegecapstoneteam1.cookingapp.util.collectLatestStateFlow

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
//        searchCooks()


        collectLatestStateFlow(viewModel.searchPagingResult) {
            recipeAdapter.submitData(it)
        }

        //플로팅 버튼을 누르면 검색
        binding.btnSearch.setOnClickListener {
            var rcpNm = binding.etSearch.text.toString()
            viewModel.searchCookingsPaging(rcpNm)


        }

//        viewModel.searchResult.observe(viewLifecycleOwner) { response ->
//            val recipes = response.cOOKRCP01.recipes
//            recipeAdapter.submitList(recipes)
//        }
//        binding.btnLeftsearch.setOnClickListener {
//            viewModel.decreaseNum()
//        }
//        binding.btnSearch.setOnClickListener {
//
//        }
//        binding.btnRightsearch.setOnClickListener {
//            viewModel.addNum()
//        }
    }

    //텍스트 입력이 주어진 후 일정 시간이 지나면 검색 시작 TEST
    private fun searchCooks() {
        var startTime = System.currentTimeMillis()
        var endTime: Long
        binding.etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime-startTime >= SEARCH_COOKS_TIME_DELAY) {
                text?.let {
                    val rcpNm = it.toString().trim()
                    if (rcpNm.isNotEmpty()) {
                        viewModel.searchCookingsPaging(rcpNm)
                    }
                }
            }
            startTime - endTime


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
                    //Toast.makeText(context,recipe.rCPNM,Toast.LENGTH_SHORT).show()
                    val action = SearchFragmentDirections.actionFragmentSearchToDetailFragment(recipe)
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
}