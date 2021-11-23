package com.siekang.ui.mainactivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.siekang.R
import com.siekang.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnFocusChangeListener {

    private var _viewBinding: ActivityMainBinding? = null
    private val binding get() = _viewBinding!!

    private lateinit var toolbar: MaterialToolbar
    private lateinit var bottomNavView: BottomNavigationView

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    /////////////////////////////////////
    //
    // OVERRIDE
    //
    /////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.layoutContentToolbar.toolbar

        setSupportActionBar(toolbar)

        bottomNavView = binding.layoutContentMain.navView

        bottomNavView.setOnNavigationItemSelectedListener {
            Timber.e("Selected ${it.title}")
            unFocusEditText()

            if (it.itemId != bottomNavView.selectedItemId)
                NavigationUI.onNavDestinationSelected(
                    it,
                    navController
                )
            true
        }

        navController = findNavController(R.id.nav_host_fragment_content_main)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            Timber.d(destination.toString())
            binding.layoutContentToolbar.toolbar.setTitle(destination.label, arguments)
        }

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

        binding.layoutContentToolbar.tietWordTranslate.onFocusChangeListener = this
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

    /////////////////////////////////////
    //
    // CLASSES METHODS
    //
    /////////////////////////////////////
    private fun MaterialToolbar.setTitle(label: CharSequence?, arguments: Bundle?) {
        if (label != null) {
            // Fill in the data pattern with the args to build a valid URI
            val title = StringBuffer()
            val fillInPattern = Pattern.compile("\\{(.+?)\\}")
            val matcher = fillInPattern.matcher(label)
            while (matcher.find()) {
                val argName = matcher.group(1)
                if (arguments != null && arguments.containsKey(argName)) {
                    matcher.appendReplacement(title, "")
                    title.append(arguments.get(argName).toString())
                } else {
                    return //returning because the argument required is not found
                }
            }
            matcher.appendTail(title)
            // setTitle("")
            // textView.text = title

            setTitle(title)
        }
    }

    private fun unFocusEditText() {
        if (binding.layoutContentToolbar.tietWordTranslate.hasFocus()) {
            binding.layoutContentToolbar.tietWordTranslate.clearFocus()
        }
    }

    fun goToHome(view: View) {
        if (R.id.navigation_home != navController.currentDestination?.id) {
            navController.navigate(R.id.navigation_home)
        }
    }


    /////////////////////////////////////
    //
    // IMPLEMENTS
    //
    /////////////////////////////////////
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            Timber.d("EditText has focus")
        } else {
            Timber.e("EditText lost focus")
        }
    }

}