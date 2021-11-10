package com.siekang.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.siekang.R
import com.siekang.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _viewBinding: ActivityMainBinding? = null
    private val binding get() = _viewBinding!!

    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavView = binding.layoutContentMain.navView

        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_library,
                R.id.navigation_notifications,
                R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Timber.d(destination.toString())
        }

        bottomNavView.setOnNavigationItemSelectedListener {
            Timber.e("Selected ${it.title}")
            if (it.itemId != bottomNavView.selectedItemId)
                NavigationUI.onNavDestinationSelected(
                    it,
                    navController
                )
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}