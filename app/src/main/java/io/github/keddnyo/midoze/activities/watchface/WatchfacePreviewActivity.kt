package io.github.keddnyo.midoze.activities.watchface

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.WatchfacePreviewAdapter
import io.github.keddnyo.midoze.local.dataModels.Watchface
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList

class WatchfacePreviewActivity : AppCompatActivity() {
    private lateinit var shareTitle: String
    private lateinit var downloadContent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        val context = this@WatchfacePreviewActivity

        if (intent.hasExtra("watchfaceArray")) {
            val position = intent.getIntExtra("position", 0)

            val watchfaceArray: ArrayList<Watchface> = GsonBuilder().create().fromJson(
                intent.getStringExtra("watchfaceArray").toString(),
                object : TypeToken<ArrayList<Watchface>>() {}.type
            )

            val description: TextView =
                findViewById(R.id.description)
            val download: ExtendedFloatingActionButton =
                findViewById(R.id.download)

            val viewPager: ViewPager2 = findViewById(R.id.firmwarePreviewPager)
            val adapter = WatchfacePreviewAdapter(watchfaceArray)
            viewPager.adapter = adapter

            viewPager.setCurrentItem(position, false)
            fun initializeViewPager(position: Int) {
                val watchface = watchfaceArray[position]

                title = watchface.title.trim().replaceFirstChar { it.uppercase() }
                supportActionBar?.subtitle = watchface.categoryName.trim().replaceFirstChar { it.uppercase() }
                shareTitle = watchface.title
                downloadContent = watchface.url

                watchface.introduction.let { content ->
                    if (content.isNotBlank() && content != "null") {
                        description.text = content
                    } else {
                        description.visibility = View.GONE
                    }
                }

                if (OnlineStatus(context).isOnline) {
                    download.isEnabled = true
                    download.setOnClickListener {
                        runBlocking(Dispatchers.IO) {
                            if (OnlineStatus(context).isOnline) {
                                Requests().getFirmwareFile(
                                    context,
                                    watchface.url,
                                    watchface.title,
                                    getString(R.string.menu_watchface)
                                )
                            } else {
                                getString(R.string.connectivity_error).showAsToast(context)
                            }
                        }
                    }
                }
            }

            initializeViewPager(position)

            viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    initializeViewPager(position)
                }
            })
        } else {
            finish()
        }
    }

    private fun shareContent() {
        val stringBuilder = StringBuilder()
        val shareContent = stringBuilder.append(shareTitle)
            .append("\n")
            .append(downloadContent)
            .append("\n")
            .append(
                getString(
                    R.string.firmware_share_get_it_on,
                    getString(R.string.app_name)
                )
            )
            .append("\n")
            .append(GITHUB_APP_REPOSITORY)
            .toString()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareContent)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, shareTitle)
        startActivity(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_response, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share_firmware -> {
                shareContent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}