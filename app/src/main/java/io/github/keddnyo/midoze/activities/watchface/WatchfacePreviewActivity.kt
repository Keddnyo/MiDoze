package io.github.keddnyo.midoze.activities.watchface

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.BitmapCache
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class WatchfacePreviewActivity : AppCompatActivity() {
    private lateinit var title: String
    private lateinit var downloadContent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        val context = this@WatchfacePreviewActivity

        if (intent.hasExtra("download")) {
            title = intent.getStringExtra("deviceName").toString()
            val subtitle = intent.getStringExtra("title").toString().trim()

            supportActionBar?.title = title
            supportActionBar?.subtitle = subtitle

            downloadContent = intent.getStringExtra("download").toString()

            val preview: ImageView =
                findViewById(R.id.preview)
            val description: TextView =
                findViewById(R.id.description)
            val download: ExtendedFloatingActionButton =
                findViewById(R.id.download)

            preview.setImageBitmap(
                BitmapCache(context).decode(
                    intent.getStringExtra("deviceAlias").toString(),
                    subtitle
                )
            )

            if (OnlineStatus(context).isOnline) {
                download.isEnabled = true
                download.setOnClickListener {
                    runBlocking(Dispatchers.IO) {
                        if (OnlineStatus(context).isOnline) {
                            Requests().getFirmwareFile(
                                context,
                                downloadContent,
                                title,
                                getString(R.string.menu_watchface)
                            )
                        } else {
                            getString(R.string.connectivity_error).showAsToast(context)
                        }
                    }
                }
            }

            intent.getStringExtra("description").toString().let { content ->
                if (content.isNotBlank() && content != "null") {
                    description.text = content
                } else {
                    description.visibility = View.GONE
                }
            }
        } else {
            finish()
        }
    }

    private fun shareContent() {
        val stringBuilder = StringBuilder()
        val shareContent = stringBuilder.append(title)
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

        val shareIntent = Intent.createChooser(sendIntent, title)
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