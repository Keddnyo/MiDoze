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

        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        when (prefs.getString("settings_default_tab", "1")) {
            "2" -> {
                replaceFragment(favFragment)
            }
            "3" -> {
                replaceFragment(extrasFragment)
            }
            else -> {
                replaceFragment(feedFragment)
            }
        }

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation)

        fun changeBadge() {
            bottomBar.getOrCreateBadge(R.id.action_fav).number = prefs.getInt("favoriteCount", 0)
        }

        changeBadge()

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
            changeBadge()
            true
        }

        when (intent.action.toString()) {
            "FEED_SHORTCUT" -> {
                replaceFragment(feedFragment)
                bottomBar.selectedItemId = R.id.action_feed
            }
            "FAVORITE_SHORTCUT" -> {
                replaceFragment(favFragment)
                bottomBar.selectedItemId = R.id.action_fav
            }
            "EXTRAS_SHORTCUT" -> {
                replaceFragment(extrasFragment)
                bottomBar.selectedItemId = R.id.action_extras
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        finish()
    }
}