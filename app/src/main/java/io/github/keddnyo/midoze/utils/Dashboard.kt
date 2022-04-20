package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.deviceList.DeviceListAdapter

class Dashboard {
    fun setFavoritesCount(context: Context, deviceIndex: String, holder: DeviceListAdapter.DeviceListViewHolder) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

        var counter = prefs.getInt("favoriteCount", 0)

        if (prefs.getBoolean(deviceIndex, false)) {
            counter--

            holder.likeIcon.setImageResource(R.drawable.ic_favorite_border)
            editor.putBoolean(deviceIndex, false)
            editor.putInt("favoriteCount", counter)
            editor.apply()
        } else {
            counter++

            holder.likeIcon.setImageResource(R.drawable.ic_favorite)
            editor.putBoolean(deviceIndex, true)
            editor.putInt("favoriteCount", counter)
            editor.apply()
        }
    }
    fun setDownloadCount(context: Context) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

        var counter = prefs.getInt("downloadCount", 0)
        counter++

        editor.putInt("downloadCount", counter)
        editor.apply()
    }
    fun setShareCount(context: Context) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

        var counter = prefs.getInt("shareCount", 0)
        counter++

        editor.putInt("shareCount", counter)
        editor.apply()
    }
}