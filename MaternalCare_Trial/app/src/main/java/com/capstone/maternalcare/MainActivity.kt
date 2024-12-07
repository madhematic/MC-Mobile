package com.capstone.maternalcare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.maternalcare.data.model.User
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.ActivityMainBinding
import com.capstone.maternalcare.ui.login.LoginActivity
import com.capstone.maternalcare.util.MainViewModel
import com.capstone.maternalcare.util.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupFragment()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Pastikan BottomNavigationView terhubung dengan benar ke NavController
        binding.navView.setupWithNavController(navController)

        // Menambahkan listener untuk memanipulasi ActionBar berdasarkan fragment yang sedang aktif
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    supportActionBar?.apply {
                        title = getString(R.string.home)
                        setDisplayHomeAsUpEnabled(false)
                    }
                }
                R.id.profilFragment -> {
                    supportActionBar?.apply {
                        title = getString(R.string.profile)
                        setDisplayHomeAsUpEnabled(false)
                    }
                }
                R.id.articlesFragment -> {
                    supportActionBar?.apply {
                        title = getString(R.string.articles)
                        setDisplayHomeAsUpEnabled(false)
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]


        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        mainViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }


}
