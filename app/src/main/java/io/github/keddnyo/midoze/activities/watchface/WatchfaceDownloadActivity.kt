package io.github.keddnyo.midoze.activities.watchface

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.BitmapCache
import io.github.keddnyo.midoze.utils.OnlineStatus
import java.io.File

class WatchfaceDownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchface_download)

        val context = this@WatchfaceDownloadActivity

        if (intent.hasExtra("watchface_download_link")) {
            val watchfaceDownloadPreview: ImageView = findViewById(R.id.watchfaceDownloadPreview)
            val watchfaceDownloadIntroduction: TextView =
                findViewById(R.id.watchfaceDownloadIntroduction)
            val watchfaceDownloadSize: TextView = findViewById(R.id.watchfaceDownloadSize)
            val watchfaceShareButton: ImageView = findViewById(R.id.watchfaceShareButton)
            val watchfaceDownloadButton: ImageView = findViewById(R.id.watchfaceDownloadButton)

            val watchfaceTitle = intent.getStringExtra("watchface_download_title")
            val watchfaceUrl = intent.getStringExtra("watchface_download_link")

            title = intent.getStringExtra("watchface_download_device_name")
            supportActionBar?.subtitle = watchfaceTitle
            watchfaceDownloadPreview.setImageBitmap(
                BitmapCache().decode(
                    intent.getStringExtra("watchface_download_preview").toString()
                )
            )
            watchfaceDownloadIntroduction.run {
                intent.getStringExtra("watchface_download_introduction").toString()
                    .let { introduction ->
                        if (introduction.isNotBlank() && introduction != "null") {
                            text = introduction
                        } else {
                            visibility = View.GONE
                        }
                    }
            }
            watchfaceDownloadSize.text = intent.getStringExtra("watchface_download_size").toString()

            watchfaceShareButton.setOnClickListener {
                val stringBuilder = StringBuilder()
                val shareContent = stringBuilder.append(watchfaceTitle)
                    .append("\n")
                    .append(watchfaceUrl)
                    .append("\n")
                    .append(getString(R.string.firmware_share_get_it_on, getString(R.string.app_name)))
                    .append("\n")
                    .append(GITHUB_APP_REPOSITORY)
                    .toString()

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareContent)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, watchfaceTitle)
                startActivity(shareIntent)
            }

            if (OnlineStatus(context).isOnline) {
                watchfaceDownloadButton.setOnClickListener {
                    Requests().getFirmwareFile(
                        context,
                        watchfaceUrl.toString(),
                        intent.getStringExtra("watchface_download_title").toString(),
                        getString(R.string.menu_watchface)
                    )
                }
            } else {
                watchfaceDownloadButton.isEnabled = false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}