package com.siekang.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.siekang.databinding.ActivitySplashscreenBinding
import com.siekang.ui.mainactivity.MainActivity
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class SplashScreenActivity : AppCompatActivity(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private var _viewBinding: ActivitySplashscreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _viewBinding!!


    /////////////////////////////////////
    //
    // OVERRIDE
    //
    /////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(coroutineContext) {
            delay(TimeUnit.SECONDS.toMillis(3))

            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        }
    }
}