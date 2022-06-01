package io.github.keddnyo.midoze.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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

        val clearFeedCache = findPreference<Preference>("settings_feed_cache_clear")
        val customRequest = findPreference<Preference>("settings_custom_request")

        val about = findPreference<Preference>("settings_app_info")
        val cloud = findPreference<Preference>("settings_server_info")
        val github = findPreference<Preference>("settings_app_github_page")

        if (clearFeedCache != null && customRequest != null && about != null && cloud != null && github != null) {

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

            customRequest.setOnPreferenceClickListener {
                startActivity(Intent(requireContext(), RequestActivity::class.java))
                true
            }

            github.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Keddnyo/MiDoze"))
                )
                true
            }

            about.title = getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME

            about.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Keddnyo"))
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
}