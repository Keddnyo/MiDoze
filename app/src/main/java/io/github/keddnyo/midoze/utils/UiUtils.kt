package io.github.keddnyo.midoze.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class UiUtils {
    fun getGridLayoutIndex(
        context: Context,
        columnWidthDp: Int
    ): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp.toString().toFloat() + 0.5).toInt()
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