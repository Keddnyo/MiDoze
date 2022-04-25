package io.github.keddnyo.midoze.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.fragments.FeedFragment
import io.github.keddnyo.midoze.fragments.ProfileFragment
import io.github.keddnyo.midoze.utils.UiUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun replaceFragment(fragment: Fragment) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        UiUtils().switchDarkMode(this)
        replaceFragment(FeedFragment())

        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_feed -> {
                    replaceFragment(FeedFragment())
                    editor.putBoolean("Favorites", false)
                    editor.apply()
                }
                R.id.action_fav -> {
                    replaceFragment(FeedFragment())
                    editor.putBoolean("Favorites", true)
                    editor.apply()
                }
                R.id.action_extras -> {
                    replaceFragment(ProfileFragment())
                }
            }
            true
        }
    }
}