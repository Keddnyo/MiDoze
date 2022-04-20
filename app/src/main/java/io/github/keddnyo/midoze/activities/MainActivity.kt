package io.github.keddnyo.midoze.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.fragments.ExtrasFragment
import io.github.keddnyo.midoze.fragments.FavoriteFragment
import io.github.keddnyo.midoze.fragments.FeedFragment
import io.github.keddnyo.midoze.fragments.SettingsFragment
import io.github.keddnyo.midoze.utils.UiUtils

class MainActivity : AppCompatActivity() {

    private val feedFragment = FeedFragment()
    private val favFragment = FavoriteFragment()
    private val extrasFragment = ExtrasFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UiUtils().switchDarkMode(this)
        replaceFragment(feedFragment)
    }

    override fun onResume() {
        super.onResume()

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_feed -> {
                    replaceFragment(feedFragment)
                }
                R.id.action_fav -> {
                    replaceFragment(favFragment)
                }
                R.id.action_extras -> {
                    replaceFragment(extrasFragment)
                }
                R.id.action_settings -> {
                    replaceFragment(settingsFragment)
                }
            }
            changeBadge(bottomBar)
            true
        }

        changeBadge(bottomBar)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun changeBadge(bottomBar: BottomNavigationView) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        bottomBar.getOrCreateBadge(R.id.action_fav).number = prefs.getInt("favoriteCount", 0)
    }

    override fun onBackPressed() {
        finish()
    }
}