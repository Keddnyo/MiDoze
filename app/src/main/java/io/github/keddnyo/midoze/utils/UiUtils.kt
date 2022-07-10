package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class UiUtils {
    fun getGridLayoutIndex(
        context: Context,
        columnWidthDp: Int,
    ): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp.toString().toFloat() + 0.5).toInt()
    }

    fun showToast(context: Context, string: String) {
        val toast = Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun switchDarkMode(context: Context) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

        when (prefs.getBoolean("settings_dark_mode", false)) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}