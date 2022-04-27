package io.github.keddnyo.midoze.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class UiUtils {
    fun getRecyclerSpanCount(context: Context): Int = with(context) {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            1
        } else {
            2
        }
    }

    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun switchDarkMode(context: Context) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        when (prefs.getString("settings_app_theme", "1")) {
            "2" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "3" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    (context as Activity).window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    (context as Activity).window.statusBarColor = ContextCompat.getColor(context, android.R.color.black)
                }
            }
        }
    }
}