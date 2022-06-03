package io.github.keddnyo.midoze.activities.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.databinding.ActivityMainBinding
import io.github.keddnyo.midoze.fragments.FeedFragment
import io.github.keddnyo.midoze.fragments.SettingsFragment
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.UiUtils
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager
    val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= 21) {

            UiUtils().switchDarkMode(context)

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
                ) {
                }

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
            
            val prefs = PreferenceManager.getDefaultSharedPreferences(this)

            class LoadDataForActivity :
                AsyncTask<Void?, Void?, Void>() {

                var releaseData: JSONObject = JSONObject("{}")

                @Deprecated("Deprecated in Java")
                override fun doInBackground(vararg p0: Void?): Void? {
                    if (prefs.getBoolean("settings_app_check_updates",
                            true) && DozeRequest().isOnline(context)
                    ) {
                        releaseData = DozeRequest().getApplicationLatestReleaseInfo(context)
                    }
                    return null
                }

                @Deprecated("Deprecated in Java")
                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)

                    if (releaseData.has("tag_name") && releaseData.getJSONArray("assets")
                            .toString() != "[]"
                    ) {
                        val latestVersion = releaseData.getString("tag_name")
                        val releaseChangelog = releaseData.getString("body")
                        val latestVersionLink =
                            releaseData.getJSONArray("assets").getJSONObject(0)
                                .getString("browser_download_url")

                        if (BuildConfig.VERSION_NAME < latestVersion) {
                            val builder = AlertDialog.Builder(context)
                                .setTitle("${getString(R.string.update_dialog_title)} $latestVersion")
                                .setMessage(releaseChangelog)
                                .setIcon(R.drawable.ic_info)
                                .setCancelable(false)
                            builder.setPositiveButton(R.string.update_dialog_button) { _: DialogInterface?, _: Int ->
                                DozeRequest().getFirmwareFile(context,
                                    latestVersionLink,
                                    getString(R.string.app_name))
                                UiUtils().showToast(context,
                                    getString(R.string.downloading_toast))
                                DialogInterface.BUTTON_POSITIVE
                            }
                            builder.setNegativeButton(android.R.string.cancel) { _: DialogInterface?, _: Int ->
                                DialogInterface.BUTTON_NEGATIVE
                            }
                            builder.show()
                        }
                    }
                }
            }
            LoadDataForActivity().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        } else {
            finish()
            startActivity(Intent(this, RequestActivity::class.java))
            UiUtils().showToast(context, getString(R.string.compatibility_mode))
        }
    }

    class MyAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
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