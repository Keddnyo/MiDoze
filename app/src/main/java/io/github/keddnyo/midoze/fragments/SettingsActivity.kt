package io.github.keddnyo.midoze.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()

        requireActivity().title = getString(R.string.settings_title)

        val favorites = findPreference<Preference>("settings_favorites")
        val downloads = findPreference<Preference>("settings_downloads")
        val shares = findPreference<Preference>("settings_shares")

        val about = findPreference<Preference>("settings_app_info")
        val cloud = findPreference<Preference>("settings_server_info")
        val github = findPreference<Preference>("settings_app_github_page")

        if (favorites != null && downloads != null && shares != null && about != null && cloud != null && github != null) {
            val prefs: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(requireActivity())

            favorites.summary = "${prefs.getInt("favoriteCount", 0)} ${getString(R.string.settings_items)}"
            downloads.summary = "${prefs.getInt("downloadCount", 0)} ${getString(R.string.settings_times)}"
            shares.summary = "${prefs.getInt("shareCount", 0)} ${getString(R.string.settings_times)}"

            about.title = getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME

            about.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Keddnyo"))
                )
                true
            }

            cloud.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://4pda.to/forum/index.php?showuser=243484"))
                )
                true
            }

            github.setOnPreferenceClickListener {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Keddnyo/MiDoze"))
                )
                true
            }
        }
    }
}