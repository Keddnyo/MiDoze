package io.github.keddnyo.midoze.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity

class FiltersFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setHasOptionsMenu(true)
        setPreferencesFromResource(R.xml.fragment_filters, rootKey)
    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = prefs.edit()

        fun getStringByKey(key: String): String? {
            return prefs.getString(key, "") // Wrong but working
        }

        findPreference<Preference>("filters_app_version_load_defaults")?.setOnPreferenceClickListener {
            editor.putString(
                "filters_zepp_app_version",
                getString(R.string.filters_request_zepp_app_version_value)
            )
            editor.putString(
                "filters_zepp_life_app_version",
                getString(R.string.filters_request_zepp_life_app_version_value)
            )
            editor.apply()

            findPreference<EditTextPreference>("filters_zepp_app_version")?.text =
                getStringByKey("filters_zepp_app_version")
            findPreference<EditTextPreference>("filters_zepp_life_app_version")?.text =
                getStringByKey("filters_zepp_life_app_version")

            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filters, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = with(requireContext()) {
        when (item.itemId) {
            R.id.action_custom_request -> {
                startActivity(Intent(requireContext(), RequestActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}