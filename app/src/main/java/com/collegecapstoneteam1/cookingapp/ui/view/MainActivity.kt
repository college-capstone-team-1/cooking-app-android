package com.collegecapstoneteam1.cookingapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.data.db.RecipeDatabase
import com.collegecapstoneteam1.cookingapp.data.repository.RecipeRepositoryImpl
import com.collegecapstoneteam1.cookingapp.databinding.ActivityMainBinding
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModel
import com.collegecapstoneteam1.cookingapp.ui.viewmodel.MainViewModelProviderFactory
import com.collegecapstoneteam1.cookingapp.util.NetworkManager
import java.util.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    val list = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.topAppBar)

        val db = RecipeDatabase.getInstance(this)
        val recipeRepositoryImpl = RecipeRepositoryImpl(db)
        val factory = MainViewModelProviderFactory(recipeRepositoryImpl)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setupJetpackNavigation()

        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_home
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val random = Random()

        while (list.size < 5) {
            val randomNumber = random.nextInt(20) + 1
            if (list.contains(randomNumber)) {
                continue
            }
            list.add(randomNumber)
        }

    }



    private fun setupJetpackNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.cookingsearch_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            //navController.graph
            setOf(
                R.id.fragment_home,
                R.id.fragment_search,
                R.id.fragment_saved,
                R.id.fragment_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun checkNetWork() = NetworkManager.checkNetworkState(this)

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}