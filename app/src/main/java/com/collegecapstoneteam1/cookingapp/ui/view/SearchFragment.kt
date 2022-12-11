package com.collegecapstoneteam1.cookingapp.ui.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
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

    var detail_search_activated = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

//        searchCooks()


        collectLatestStateFlow(viewModel.searchPagingResult) {
            recipeAdapter.submitData(it)
        }

        binding.btnSearch.setOnClickListener {
            var rcpNm = binding.etSearch.text.toString()
            viewModel.searchCookingsPaging(rcpNm)
        }



        //detail Search
        binding.btnUp.isActivated = detail_search_activated

        if (binding.btnUp.isActivated){
            binding.detailForm.setHeightWrap()
        }else{
            binding.detailForm.setHeight(0)
        }

        (binding.detailForm as ViewGroup).layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        (binding.rvForm as ViewGroup).layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        binding.btnUp.setOnClickListener {
            if (it.isActivated){
                binding.detailForm.setHeight(0)
            }else{
                binding.detailForm.setHeightWrap()
            }
            it.isActivated = !it.isActivated
            detail_search_activated = it.isActivated
        }



        val sort_list1 = listOf("모두","국&찌개","기타","반찬","밥","일품","후식")
        val adapter_spinner1 =  ArrayAdapter(context as MainActivity, R.layout.itme_dropdown, sort_list1)
        binding.spType1.setAdapter(adapter_spinner1)

        val sort_list2 = listOf("모두","굽기","기타","끓이기","볶기","찌기","튀기기")
        val adapter_spinner2 =  ArrayAdapter(context as MainActivity, R.layout.itme_dropdown, sort_list2)
        binding.spType2.setAdapter(adapter_spinner2)

        binding.btnDetailSearch.setOnClickListener {
            val pos1 = binding.spType1.selectedItemPosition
            val pos2 = binding.spType2.selectedItemPosition
            viewModel.searchCookingsPaging(
                name = binding.etName.text.toString(),
                detail = binding.etDetail.text.toString(),
                part = if(pos1 == 0) "" else sort_list1[pos1],
                way = if(pos2 == 0) "" else sort_list2[pos2],
            )
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

    fun LinearLayout.setHeight(num: Int) {
        var layoutParam = this.layoutParams
        layoutParam.height = num
        //(this as ViewGroup).layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        this.layoutParams = layoutParam
    }

    fun LinearLayout.setHeightWrap() {
        var layoutParam = this.layoutParams
        layoutParam.height = LayoutParams.WRAP_CONTENT
        //(this as ViewGroup).layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        this.layoutParams = layoutParam
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
                    0
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