package io.github.keddnyo.midoze.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import io.github.keddnyo.midoze.R

class FiltersFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_filters, rootKey)
    }
}