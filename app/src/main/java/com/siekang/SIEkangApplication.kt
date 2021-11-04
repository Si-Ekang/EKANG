package com.siekang

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SIEkangApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        init()
    }

    private fun init() {
        PACKAGE_NAME = packageName

        if (BuildConfig.DEBUG) {
            // Timber : logging
            Timber.plant(Timber.DebugTree())
        }
    }

    fun getContext(): Context {
        return super.getApplicationContext()
    }

    fun getLabPackageName(): String? {
        return PACKAGE_NAME
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    @SuppressLint("BinaryOperationInTimber")
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        if (level == TRIM_MEMORY_UI_HIDDEN) {
            notifyAppInBackground()
        }

        if (level == TRIM_MEMORY_RUNNING_LOW) {
            Timber.e(
                "The device is running much lower on memory. " +
                        "Your app is running and not killable," +
                        " but please release unused resources to improve system performance"
            )
        }
    }

    private fun notifyAppInBackground() {
        Timber.e("App went in background")
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    companion object {
        private var PACKAGE_NAME: String? = null
        private var mInstance: SIEkangApplication? = null

        @Synchronized
        fun getInstance(): SIEkangApplication {

            if (null == mInstance) {
                mInstance = SIEkangApplication()
            }

            return mInstance as SIEkangApplication
        }
    }
}