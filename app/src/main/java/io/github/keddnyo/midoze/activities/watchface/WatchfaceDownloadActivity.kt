package io.github.keddnyo.midoze.activities.watchface

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
import io.github.keddnyo.midoze.utils.BitmapCache
import io.github.keddnyo.midoze.utils.OnlineStatus

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
            val watchfaceDownloadButton: FloatingActionButton = findViewById(R.id.watchfaceDownloadButton)

            title = intent.getStringExtra("watchface_download_device_name")
            supportActionBar?.subtitle = intent.getStringExtra("watchface_download_title")
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}