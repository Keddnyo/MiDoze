package io.github.keddnyo.midoze.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.utils.UiUtils

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val clearFeedCache = findPreference<Preference>("settings_feed_cache_clear")

        val about = findPreference<Preference>("settings_app_info")
        val cloud = findPreference<Preference>("settings_server_info")

        if (clearFeedCache != null && about != null && cloud != null) {

            clearFeedCache.setOnPreferenceClickListener {
                val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                val editor = prefs.edit()
                editor.putString("Firmwares", "")
                editor.apply()
                Toast.makeText(requireContext(),
                    R.string.settings_feed_cache_cleared,
                    Toast.LENGTH_SHORT).show()
                true
            }

            about.title = getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME

            about.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Keddnyo/MiDoze"))
                )
                true
            }

            cloud.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://4pda.to/forum/index.php?showuser=243484"))
                )
                true
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Unit = with(requireContext()) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val settingsItem = menu.findItem(R.id.action_dark_mode)
        settingsItem.icon = if (prefs.getBoolean("settings_dark_mode", false)) {
            ContextCompat.getDrawable(this, R.drawable.ic_light_mode)
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_dark_mode)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = with(requireContext()) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = prefs.edit()

        when (item.itemId) {
            R.id.action_dark_mode -> {
                if (prefs.getBoolean("settings_dark_mode", false)) {
                    editor.putBoolean("settings_dark_mode", false)
                    editor.apply()
                } else {
                    editor.putBoolean("settings_dark_mode", true)
                    editor.apply()
                }
                UiUtils().switchDarkMode(this)
            }
            R.id.action_custom_request -> {
                startActivity(Intent(requireContext(), RequestActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}