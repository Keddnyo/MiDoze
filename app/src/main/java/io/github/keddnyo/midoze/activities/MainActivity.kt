package io.github.keddnyo.midoze.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.fragments.ProfileFragment
import io.github.keddnyo.midoze.fragments.FavoriteFragment
import io.github.keddnyo.midoze.fragments.FeedFragment
import io.github.keddnyo.midoze.utils.UiUtils

class MainActivity : AppCompatActivity() {

    private val feedFragment = FeedFragment()
    private val favFragment = FavoriteFragment()
    private val extrasFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UiUtils().switchDarkMode(this)

        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation)

        fun openFragment(index: Int) {
            when (index) {
                1 -> {
                    replaceFragment(feedFragment)
                    bottomBar.selectedItemId = R.id.action_feed
                }
                2 -> {
                    replaceFragment(favFragment)
                    bottomBar.selectedItemId = R.id.action_fav
                }
                3 -> {
                    replaceFragment(extrasFragment)
                    bottomBar.selectedItemId = R.id.action_extras
                }
            }
        }

        when (prefs.getString("settings_default_tab", "1")) {
            "2" -> {
                openFragment(2)
            }
            "3" -> {
                openFragment(3)
            }
            else -> {
                openFragment(1)
            }
        }

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
            }
            true
        }

        when (intent.action.toString()) {
            "FEED_SHORTCUT" -> {
                openFragment(1)
            }
            "FAVORITE_SHORTCUT" -> {
                openFragment(2)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}