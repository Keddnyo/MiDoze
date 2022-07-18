package io.github.keddnyo.midoze.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.remote.Routes

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setHasOptionsMenu(true)
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        findPreference<Preference>("settings_credentials")?.title =
            "${getString(R.string.app_name)} ${BuildConfig.VERSION_NAME}"

        findPreference<Preference>("settings_credentials")?.setOnPreferenceClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(Routes.GITHUB_PROJECT_PAGE))
            )
            true
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
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = with(requireContext()) {
        when (item.itemId) {
            R.id.action_request -> {
                startActivity(Intent(context, RequestActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}