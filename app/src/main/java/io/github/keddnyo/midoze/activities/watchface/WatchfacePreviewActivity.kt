package io.github.keddnyo.midoze.activities.watchface

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.BitmapCache
import io.github.keddnyo.midoze.utils.FirmwarePreview
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class WatchfacePreviewActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var title: String
    private lateinit var downloadContent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        context = this@WatchfacePreviewActivity

        if (intent.hasExtra("download")) {
            title = intent.getStringExtra("title").toString()
            val subtitle = intent.getStringExtra("subtitle").toString()

            supportActionBar?.title = title
            supportActionBar?.subtitle = subtitle

            downloadContent = intent.getStringExtra("download").toString()

            class Preview : FirmwarePreview() {
                override var preview: ImageView =
                    findViewById(R.id.preview)
                override var description: TextView =
                    findViewById(R.id.description)

                override fun main() {
                    super.main()

                    preview.setImageBitmap(
                        BitmapCache().decode(
                            intent.getStringExtra("preview").toString()
                        )
                    )

                    intent.getStringExtra("description").toString().let { content ->
                        if (content.isNotBlank() && content != "null") {
                            description.text = content
                        } else {
                            description.visibility = View.GONE
                        }
                    }
                }
            }

            Preview().main()
        }
    }

    private fun downloadContent() {
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
        menuInflater.inflate(R.menu.menu_preview, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.preview_share -> {
                shareContent()
            }
            R.id.preview_download -> {
                if (OnlineStatus(context).isOnline) {
                    runBlocking(Dispatchers.IO) {
                        downloadContent()
                    }
                } else {
                    getString(R.string.connectivity_error).showAsToast(context)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}