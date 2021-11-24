package com.siekang.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.lang.reflect.Field


/**
 * Class that check compatibility, SDK's version and stuff to be sure that your device can handle the actions
 *
 * This class can't be instantiated.
 */
class CompatibilityManager private constructor() {

    companion object {
        /**
         * Get the current Android API level.
         */
        private fun getSdkVersion(): Int {
            return Build.VERSION.SDK_INT
        }

        /**
         * Determine if the device is running API level 8 or higher.
         */
        fun isFroyo(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.FROYO
        }

        /**
         * Determine if the device is running API level 11 or higher.
         */
        private fun isHoneycomb(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.HONEYCOMB
        }

        /**
         * Determine if the device is running API level 19 or higher.
         */
        fun isKitkat(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.KITKAT
        }

        /**
         * Determine if the device is running API level 21 or higher.
         */
        fun isLollipop(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.LOLLIPOP
        }

        /**
         * Determine if the device is running API level is higher than 21.
         */
        fun isLollipopPlus(): Boolean {
            return getSdkVersion() > Build.VERSION_CODES.LOLLIPOP
        }

        /**
         * Determine if the device is running API level 23 or higher.
         */
        fun isMarshmallow(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.M
        }

        /**
         * Determine if the device is running API level 24 or higher.
         */
        fun isNougat(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.N
        }

        /**
         * Determine if the device is running API level 26 or higher.
         */
        fun isOreo(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.O
        }

        /**
         * Determine if the device is running API level 28 or higher.
         */
        fun isPie(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.P
        }


        /**
         * Determine if the device is running API level 29 or higher.
         * Quince Tart
         */
        fun isAndroid10(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.Q
        }


        /**
         * Determine if the device is running API level 30 or higher.
         * Red Velvet Cake
         */
        fun isR(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.R
        }


        /**
         * Determine if the device is running API level 31 or higher.
         * Snow Cone
         */
        fun isS(): Boolean {
            return getSdkVersion() >= Build.VERSION_CODES.S
        }


        /**
         * Determine if the device is a tablet (i.e. it has a large screen).
         *
         * @param context The calling context.
         */
        private fun isTablet(context: Context): Boolean {
            return (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        }

        /**
         * Determine if the device is a HoneyComb tablet.
         *
         * @param context The calling context.
         */
        fun isHoneycombTablet(context: Context): Boolean {
            return isHoneycomb() && isTablet(context)
        }


        @SuppressLint("NewApi")
        fun getOSName(): String {
            var fields: Array<Field>? = Build.VERSION_CODES::class.java.fields
            var osName = "UNKNOWN"
            if (fields != null) {
                for (field in fields) {
                    try {
                        if (field.getInt(Build.VERSION_CODES::class.java) == Build.VERSION.SDK_INT - 1) {
                            osName = field.name
                            Timber.e("code name %s", osName)
                            if (isOreo()) {
                                osName = getOsVersionName(osName)
                            }
                        }
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            }

            // TEST
            fields = null
            val builder = StringBuilder()
            builder.append("android : ").append(Build.VERSION.RELEASE)
            fields = Build.VERSION_CODES::class.java.fields
            for (field in fields) {
                val fieldName = field.name
                var fieldValue = -1
                try {
                    fieldValue = field.getInt(Any())
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
                if (fieldValue == Build.VERSION.SDK_INT) {
                    builder.append(" : ")
                        .append(fieldName).append(" : ")
                        .append("sdk=").append(fieldValue)
                }
            }
            fields = null
            Timber.e("OS: %s", builder.toString())
            // TEST
            return osName
        }


        /**
         * Gets the version name from version code. Note! Needs to be updated
         * when new versions arrive, or will return a single letter. Like Android 8.0 - Oreo
         * yields "O" as a version name.
         *
         * @return version name of device's OS
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        fun getOsVersionName(name: String): String {
            if (name == "Q") return "Android 10"
            if (name == "P") return "Pie"
            if (name == "O") return "Oreo"
            if (name == "N") return "Nougat"
            if (name == "M") return "Marshmallow"
            if (name.startsWith("O_")) return "Oreo++"
            return if (name.startsWith("N_")) "Nougat++" else "UNKNOWN"
        }
    }
}

/**
 * Android version table
 * <p>
 * SDK_INT value        Build.VERSION_CODES        Human Version Name
 * 1                  BASE                      Android 1.0 (no codename)
 * 2                  BASE_1_1                  Android 1.1 Petit Four
 * 3                  CUPCAKE                   Android 1.5 Cupcake
 * 4                  DONUT                     Android 1.6 Donut
 * 5                  ECLAIR                    Android 2.0 Eclair
 * 6                  ECLAIR_0_1                Android 2.0.1 Eclair
 * 7                  ECLAIR_MR1                Android 2.1 Eclair
 * 8                  FROYO                     Android 2.2 Froyo
 * 9                  GINGERBREAD               Android 2.3 Gingerbread
 * 10                  GINGERBREAD_MR1           Android 2.3.3 Gingerbread
 * 11                  HONEYCOMB                 Android 3.0 Honeycomb
 * 12                  HONEYCOMB_MR1             Android 3.1 Honeycomb
 * 13                  HONEYCOMB_MR2             Android 3.2 Honeycomb
 * 14                  ICE_CREAM_SANDWICH        Android 4.0 Ice Cream Sandwich
 * 15                  ICE_CREAM_SANDWICH_MR1    Android 4.0.3 Ice Cream Sandwich
 * 16                  JELLY_BEAN                Android 4.1 Jellybean
 * 17                  JELLY_BEAN_MR1            Android 4.2 Jellybean
 * 18                  JELLY_BEAN_MR2            Android 4.3 Jellybean
 * 19                  KITKAT                    Android 4.4 KitKat
 * 20                  KITKAT_WATCH              Android 4.4 KitKat Watch
 * 21                  LOLLIPOP                  Android 5.0 Lollipop
 * 22                  LOLLIPOP_MR1              Android 5.1 Lollipop
 * 23                  M                         Android 6.0 Marshmallow
 * 24                  N                         Android 7.0 Nougat
 * 25                  N_MR1                     Android 7.1.1 Nougat
 * 26                  O                         Android 8.0 Oreo
 * 27                  O_MR1                     Android 8 Oreo MR1
 * 28                  P                         Android 9 Pie
 * 29                  Q                         Android 10
 * 30                  R                         Android 11
 * 31                  S                         Android 12
 * 10000                CUR_DEVELOPMENT           Current Development Version
 */