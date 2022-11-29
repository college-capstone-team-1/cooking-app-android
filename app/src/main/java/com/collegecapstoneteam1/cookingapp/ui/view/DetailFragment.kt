package com.collegecapstoneteam1.cookingapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.databinding.FragmentDetailBinding
import com.collegecapstoneteam1.cookingapp.ui.adapter.RecipeDetailAdapter
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel
import com.collegecapstoneteam1.cookingapp.util.NetworkManager
import com.collegecapstoneteam1.cookingapp.util.collectLatestStateFlow
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    private lateinit var viewModel: MainViewModel
    private lateinit var detailAdapter: RecipeDetailAdapter

    private var saved_state = false
    private var favorite_state = false

    lateinit var auth: FirebaseAuth
    lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe = args.recipe

        activity = getActivity() as MainActivity
        viewModel = (activity as MainActivity).viewModel

        binding.tvRecipeTitle.text = recipe.rcpNm
        binding.tvRecipePart.text = recipe.rcpPartsDtls

        Glide.with(view.context)
            .load(recipe.attFileNoMain)
            .into(binding.ivRecipeMainImg)

        setupRecyclerView()
        detailAdapter.submitList(recipe.getDetailList())

        collectLatestStateFlow(viewModel.savedRecipes) {
            saved_state = it.contains(recipe)
            binding.ivRecipeSaved.isActivated = saved_state
        }


        auth.currentUser?.let {
            if (activity.checkNetWork()) viewModel.getUsersFavorite(it.uid)
        }
        viewModel.usersFavorite.observe(viewLifecycleOwner) {
            favorite_state = viewModel.isInFavoite(recipe.rcpSeq)
            binding.ivRecipeFavorite.isActivated = favorite_state
        }

        binding.ivRecipeSaved.setOnClickListener {
            if (saved_state) {
                viewModel.deleteRecipe(recipe)
                Snackbar.make(view, "Recipe has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveRecipe(recipe)
                    }
                }.show()
            } else {
                viewModel.saveRecipe(recipe)
                Snackbar.make(view, "Recipe has Saved", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.deleteRecipe(recipe)
                    }
                }.show()
            }
        }

        binding.ivRecipeFavorite.setOnClickListener {
            if (!activity.checkNetWork()) {
                Toast.makeText(context, "네트워크를 확인해주세요", Toast.LENGTH_SHORT).show()
            } else if (auth.currentUser == null) {
                Toast.makeText(context, "로그인이 되어있지 않습니다 로그인을 해주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (favorite_state) {
                    viewModel.unFavoriteRecipePost(auth.currentUser!!.uid, recipe.rcpSeq)
                } else {
                    viewModel.favoriteRecipePost(auth.currentUser!!.uid, recipe.rcpSeq)
                }
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

    companion object {
        private const val TAG = "DetailFragment"
    }
}