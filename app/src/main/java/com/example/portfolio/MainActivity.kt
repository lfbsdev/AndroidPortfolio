package com.example.portfolio

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.portfolio.about.Profile
import com.example.portfolio.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_component_catalog, R.id.number_guesser, R.id.about_me
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.mainActivityState.collect {
                    updateNavHeader(it.currentProfile)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val button = menu.findItem(R.id.action_settings)
        button.setOnMenuItemClickListener {
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            if (navController.currentDestination?.label != getString(R.string.action_settings)) {
                navController.navigate(R.id.action_settings)
            }
            true
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun updateNavHeader(profile: Profile) {
        val navHeaderView = binding.navView.getHeaderView(0)

        val profilePictureView = navHeaderView.findViewById<ImageView>(R.id.current_profile_picture)
        profilePictureView.setImageDrawable(AppCompatResources.getDrawable(this, profile.profilePictureID))

        val profileNameView = navHeaderView.findViewById<TextView>(R.id.current_profile_name)
        profileNameView.text = profile.name

        val profileEmailView = navHeaderView.findViewById<TextView>(R.id.current_profile_email)
        profileEmailView.text = profile.email
    }
}