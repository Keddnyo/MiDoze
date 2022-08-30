package io.github.keddnyo.midoze.activities.watchface

import android.content.Intent
import android.os.Bundle
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

class FirmwarePreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        val context = this@FirmwarePreviewActivity

        if (intent.hasExtra("download")) {
            val title = intent.getStringExtra("title").toString()
            val subtitle = intent.getStringExtra("subtitle").toString()

            supportActionBar?.title = title
            supportActionBar?.subtitle = subtitle

            class Preview : FirmwarePreview() {
                override var preview: ImageView =
                    findViewById(R.id.preview)
                override var description: TextView =
                    findViewById(R.id.description)
                override var payload: TextView =
                    findViewById(R.id.payload)
                override var share: ImageView =
                    findViewById(R.id.share)
                override var download: ImageView =
                    findViewById(R.id.download)
                override var downloadContent: String = intent.getStringExtra("download").toString()

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
                    payload.text = intent.getStringExtra("payload").toString()
                }

                override fun setOnShareClickListener(unit: () -> Unit) {
                    super.setOnShareClickListener(unit)
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

                override fun setOnDownloadClickListener(unit: () -> Unit) {
                    super.setOnDownloadClickListener(unit)

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

            Preview().main()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}