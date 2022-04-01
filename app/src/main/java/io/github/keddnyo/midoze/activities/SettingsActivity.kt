package io.github.keddnyo.midoze.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onResume() {
            super.onResume()

            val extras = findPreference<Preference>("settings_custom_request")
            val about = findPreference<Preference>("settings_app_info")
            val cloud = findPreference<Preference>("settings_server_info")

            if (extras != null && about != null && cloud != null) {
                about.title = getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME

                extras.setOnPreferenceClickListener {
                    startActivity(
                        Intent(extras.context, ExtrasRequestActivity::class.java)
                    )
                    true
                }

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
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}