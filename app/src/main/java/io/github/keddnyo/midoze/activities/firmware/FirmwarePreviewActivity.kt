package io.github.keddnyo.midoze.activities.firmware

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.ResponseActivity
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.BitmapCache
import io.github.keddnyo.midoze.utils.FirmwarePreview
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import io.github.keddnyo.midoze.utils.StringUtils.toChangelog
import io.github.keddnyo.midoze.utils.StringUtils.toLanguageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.lang.StringBuilder

class FirmwarePreviewActivity : AppCompatActivity() {
    private var firmwareResponse = JSONObject()

    private val responseFirmwareTagsArray = arrayOf(
        "firmwareUrl",
        "resourceUrl",
        "baseResourceUrl",
        "fontUrl",
        "gpsUrl"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        val context = this@FirmwarePreviewActivity

        fun openResponseActivity() {
            val intent = Intent(context, ResponseActivity::class.java)
            intent.putExtra("json", firmwareResponse.toString())
            startActivity(intent)
        }

        val deviceIconValue = intent.getIntExtra("preview", R.drawable.amazfit_bip)
        firmwareResponse = JSONObject(intent.getStringExtra("data").toString())

        if (intent.hasExtra("preview")) {
            val title = intent.getStringExtra("title").toString()
            val subtitle = firmwareResponse.getString("firmwareVersion")

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

                    preview.setImageResource(
                        deviceIconValue
                    )

                    if (firmwareResponse.has("changeLog")) {
                        description.text = firmwareResponse.getString("lang").toLanguageList()
                    } else {
                        description.visibility = View.GONE
                    }
                    payload.text = intent.getStringExtra("buildTime").toString()

                    share.setOnClickListener {
                        shareFirmware()
                    }

                    download.setOnClickListener {
                        if (OnlineStatus(context).isOnline) {
                            runBlocking(Dispatchers.IO) {
                                getFirmware(firmwareResponse, context, title)
                            }
                        } else {
                            getString(R.string.connectivity_error).showAsToast(context)
                        }
                    }

                    download.setOnLongClickListener {
                        openResponseActivity()
                        true
                    }
                }
            }

            Preview().main()
        } else {
            finish()
        }
    }

    private fun shareFirmware() {
        val firmwareLinks = arrayListOf<String>()

        for (i in responseFirmwareTagsArray) {
            if (firmwareResponse.has(i)) {
                firmwareLinks.add(firmwareResponse.getString(i))
            }
        }

        val shareContent = StringBuilder().append(title)
            .append("\n")
            .append(firmwareLinks.joinToString("\n"))
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

        val shareIntent = Intent.createChooser(sendIntent, title)
        startActivity(shareIntent)
    }

    private fun getFirmware(
        jsonObject: JSONObject,
        context: Context,
        deviceName: String,
    ) {
        for (i in responseFirmwareTagsArray) {
            if (jsonObject.has(i)) {
                val urlString = jsonObject.getString(i)
                Requests().getFirmwareFile(
                    context,
                    urlString,
                    deviceName,
                    getString(R.string.menu_firmwares)
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}