package io.github.keddnyo.midoze.activities.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.databinding.ActivityMainBinding
import io.github.keddnyo.midoze.fragments.FeedFragment
import io.github.keddnyo.midoze.fragments.SettingsFragment
import io.github.keddnyo.midoze.utils.StringUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyAdapter(this, supportFragmentManager)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        val bottomBar = binding.bottomBar

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomBar.menu.findItem(R.id.menu_feed).isChecked = true
                        title = getString(R.string.feed_title)
                    }
                    1 -> {
                        bottomBar.menu.findItem(R.id.menu_settings).isChecked = true
                        title = getString(R.string.settings_title)
                    }
                }
            }
        })

        bottomBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_feed -> viewPager.currentItem = 0
                R.id.menu_settings -> viewPager.currentItem = 1
                else -> viewPager.currentItem = 0
            }
            true
        }

        // Default tab
        viewPager.currentItem = 0
        title = getString(R.string.feed_title)
    }

    class MyAdapter (private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FeedFragment()
                1 -> SettingsFragment()
                else -> FeedFragment()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return context.resources.getString(StringUtils().tabTitles[position])
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem != 0) {
            viewPager.currentItem = 0
        } else {
            super.onBackPressed()
        }
    }
}