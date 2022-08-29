package io.github.keddnyo.midoze.activities.watchface

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.BitmapCache
import io.github.keddnyo.midoze.utils.OnlineStatus

class WatchfaceDownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchface_download)

        val context = this@WatchfaceDownloadActivity

        if (intent.hasExtra("watchface_download_link")) {
            val watchfaceDownloadTitle: TextView = findViewById(R.id.watchfaceDownloadTitle)
            val watchfaceDownloadDeviceName: TextView =
                findViewById(R.id.watchfaceDownloadDeviceName)
            val watchfaceDownloadPreview: ImageView = findViewById(R.id.watchfaceDownloadPreview)
            val watchfaceDownloadIntroduction: TextView =
                findViewById(R.id.watchfaceDownloadIntroduction)
            val watchfaceDownloadButton: Button = findViewById(R.id.watchfaceDownloadButton)

            watchfaceDownloadTitle.text = intent.getStringExtra("watchface_download_title")
            watchfaceDownloadDeviceName.text = intent.getStringExtra("watchface_download_device_name")
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

            if (OnlineStatus(context).isOnline) {
                watchfaceDownloadButton.setOnClickListener {
                    Requests().getFirmwareFile(
                        context,
                        intent.getStringExtra("watchface_download_link").toString(),
                        intent.getStringExtra("watchface_download_title").toString(),
                        getString(R.string.menu_watchface)
                    )
                }
            } else {
                watchfaceDownloadButton.isEnabled = false
            }
        }
    }
}