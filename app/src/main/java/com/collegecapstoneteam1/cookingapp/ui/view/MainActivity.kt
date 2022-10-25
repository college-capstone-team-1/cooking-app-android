package com.collegecapstoneteam1.cookingapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.repository.RecipeRepositoryImpl
import com.collegecapstoneteam1.cookingapp.databinding.ActivityMainBinding
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val recipeRepositoryImpl = RecipeRepositoryImpl()
    private val factory = MainViewModelProviderFactory(recipeRepositoryImpl)
    val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupBottomNavigationView()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SearchFragment())
                        .commit()
                    true
                }
                R.id.fragment_favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, FavoriteFragment())
                        .commit()
                    true
                }
                R.id.fragment_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SettingsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}