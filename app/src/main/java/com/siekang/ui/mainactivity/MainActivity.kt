package com.siekang.ui.mainactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.siekang.R
import com.siekang.databinding.ActivityMainBinding
import com.siekang.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnFocusChangeListener, View.OnTouchListener,
    TextWatcher {

    private var _viewBinding: ActivityMainBinding? = null
    private val binding get() = _viewBinding!!

    private val mViewModel: MainActivityViewModel by viewModels()

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

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.layoutContentToolbar.toolbar
        setSupportActionBar(toolbar)

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

        setListeners()
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
    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            Timber.d("navController.addOnDestinationChangedListener : $destination")
            binding.layoutContentToolbar.toolbar.setTitle(destination.label, arguments)
        }

        bottomNavView.setOnNavigationItemSelectedListener {
            Timber.d("bottomNavView.setOnNavigationItemSelectedListener()")
            unFocusEditText()

            Timber.e("Selected ${it.title}")

            if (it.itemId != bottomNavView.selectedItemId)
                NavigationUI.onNavDestinationSelected(
                    it,
                    navController
                )
            true
        }

        binding.layoutContentToolbar.tietWordTranslate.addTextChangedListener(this)
        binding.layoutContentToolbar.tietWordTranslate.setOnTouchListener(this)
        binding.layoutContentToolbar.tietWordTranslate.onFocusChangeListener = this
    }

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
        Timber.e("unFocusEditText()")
        if (binding.layoutContentToolbar.tietWordTranslate.hasFocus()) {
            binding.layoutContentToolbar.tietWordTranslate.clearFocus()
        }
    }

    fun goToHome(view: View) {
        if (R.id.navigation_home != navController.currentDestination?.id) {
            navController.navigate(R.id.navigation_home)
        }
    }

    private fun showSearchFragment() {
        Timber.d("showSearchFragment()")
        val searchFragment = SearchFragment.newInstance()

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_fragment_container, searchFragment, SearchFragment.TAG)
        transaction.setCustomAnimations(
            R.animator.slide_down, 0,
            0, R.animator.slide_up
        )
        transaction.commit()
    }

    private fun hideSearchFragment() {
        Timber.e("hideSearchFragment()")
        val fragmentToRemove: Fragment =
            supportFragmentManager.findFragmentByTag(SearchFragment.TAG)!!

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.remove(fragmentToRemove)
        transaction.commit()
    }


    /////////////////////////////////////
    //
    // IMPLEMENTS
    //
    /////////////////////////////////////
    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            Timber.e("EditText lost focus")
            hideSearchFragment()
        } else {
            Timber.d("EditText has focus")
            showSearchFragment()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {

        when (view?.id) {
            R.id.tiet_word_translate -> {

                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3

                if (event?.action == MotionEvent.ACTION_DOWN) {
                    if (null != binding.layoutContentToolbar.tietWordTranslate.compoundDrawables[DRAWABLE_RIGHT]) {
                        if (event.rawX >= binding.layoutContentToolbar.tietWordTranslate.right - binding.layoutContentToolbar.tietWordTranslate.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                            // your action here
                            return false
                        }
                    }
                }
            }

            else -> return false
        }

        return super.onTouchEvent(event)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Ignored
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Ignored
    }

    override fun afterTextChanged(s: Editable?) {
        Timber.e("afterTextChanged() : ${s.toString()}")

        binding.layoutContentToolbar.tietWordTranslate.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_search),
            null,
            if (s.toString().isEmpty()) null else ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.ic_close
            ),
            null
        )
    }
}