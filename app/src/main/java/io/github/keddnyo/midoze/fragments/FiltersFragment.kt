package io.github.keddnyo.midoze.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.preference.PreferenceFragmentCompat
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity

class FiltersFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setHasOptionsMenu(true)
        setPreferencesFromResource(R.xml.fragment_filters, rootKey)
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