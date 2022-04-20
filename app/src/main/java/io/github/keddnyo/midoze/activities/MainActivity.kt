package io.github.keddnyo.midoze.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.fragments.FavoriteFragment
import io.github.keddnyo.midoze.fragments.FeedFragment
import io.github.keddnyo.midoze.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val feedFragment = FeedFragment()
    private val favFragment = FavoriteFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                R.id.action_settings -> {
                    replaceFragment(settingsFragment)
                }
            }
            changeBadge(bottomBar)
            true
        }

        changeBadge(bottomBar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun changeBadge(bottomBar: BottomNavigationView) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)

        bottomBar.getOrCreateBadge(R.id.action_fav).number = prefs.getInt("favoriteCount", 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_custom_request -> {
                startActivity(Intent(this, ExtrasRequestActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }
}