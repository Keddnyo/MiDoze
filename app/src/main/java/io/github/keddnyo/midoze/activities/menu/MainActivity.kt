package io.github.keddnyo.midoze.activities.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.request.RequestActivity
import io.github.keddnyo.midoze.adapters.menu.MenuAdapter
import io.github.keddnyo.midoze.remote.Routes
import io.github.keddnyo.midoze.remote.Updates

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Updates(this@MainActivity).execute()

        findViewById<BottomNavigationView>(R.id.mainNavigationBar).let { navigationBar ->
            findViewById<ViewPager>(R.id.mainViewPager).run {
                adapter = MenuAdapter(context, supportFragmentManager)

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        // TODO: Not Implemented Yet
                    }

                    override fun onPageSelected(position: Int) {
                        fun setItemChecked(item: Int) {
                            navigationBar.menu.findItem(item).isChecked = true
                        }

                        when (position) {
                            0 -> {
                                title = getString(R.string.menu_firmware)
                                setItemChecked(R.id.menu_firmware)
                            }
                            1 -> {
                                title = getString(R.string.menu_watchface)
                                setItemChecked(R.id.menu_watchface)
                            }
                            2 -> {
                                title = getString(R.string.menu_application)
                                setItemChecked(R.id.menu_application)
                            }
                        }
                    }
                })

                navigationBar.setOnNavigationItemSelectedListener { item->
                    currentItem = when (item.itemId) {
                        R.id.menu_firmware -> 0
                        R.id.menu_watchface -> 1
                        R.id.menu_application -> 2
                        else -> 0
                    }
                    true
                }

                title = getString(R.string.menu_firmware)
                currentItem = 0
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_request -> {
                startActivity(
                    Intent(this@MainActivity, RequestActivity::class.java)
                )
            }
            R.id.menu_github -> {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(Routes.GITHUB_REPOSITORY))
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}