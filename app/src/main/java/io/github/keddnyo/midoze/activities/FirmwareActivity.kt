package io.github.keddnyo.midoze.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject

class FirmwareActivity : AppCompatActivity() {

    private val context = this@FirmwareActivity

    private var firmwareResponse = JSONObject()
    private var deviceNameValue: String = ""

    private val responseFirmwareTagsArray = arrayOf(
        "firmwareUrl",
        "resourceUrl",
        "baseResourceUrl",
        "fontUrl",
        "gpsUrl"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        UiUtils().switchDarkMode(this)

        runBlocking {
            withContext(Dispatchers.IO) {
                init()
            }
        }
    }

    private suspend fun init() = withContext(Dispatchers.IO) {
        val deviceNameTextView: TextView = findViewById(R.id.deviceNameTextView)
        val deviceIconTextView: ImageView = findViewById(R.id.deviceIconImageView)
        val firmwareVersionTextView: TextView = findViewById(R.id.firmwareVersionTextView)
        val firmwareChangelogTextView: TextView = findViewById(R.id.firmwareChangelogTextView)
        val firmwareChangelogLayout: MaterialCardView = findViewById(R.id.firmware_changelog_layout)
        val firmwareLanguagesTextView: TextView = findViewById(R.id.firmwareLanguagesTextView)
        val firmwareDownloadButton: Button = findViewById(R.id.firmwareDownloadButton)

        deviceNameValue = intent.getStringExtra("deviceName").toString()
        val deviceSourceValue = intent.getIntExtra("deviceSource", 0).toString()
        val productionSourceValue = intent.getStringExtra("productionSource").toString()
        val appNameValue = intent.getStringExtra("appname").toString()
        val appVersionValue = intent.getStringExtra("appVersion").toString()

        firmwareResponse = DozeRequest().getFirmwareLinks(
            productionSourceValue,
            deviceSourceValue,
            appVersionValue,
            appNameValue,
            context
        )

        deviceNameTextView.text = deviceNameValue

        when {
            deviceNameValue.contains("Mi Band", true) -> {
                deviceIconTextView.setImageResource(R.drawable.ic_xiaomi)
            }
            deviceNameValue.contains("Zepp", true) -> {
                deviceIconTextView.setImageResource(R.drawable.ic_zepp)
            }
            else -> {
                deviceIconTextView.setImageResource(R.drawable.ic_amazfit)
            }
        }

        if (firmwareResponse.has("firmwareVersion")) {
            firmwareVersionTextView.text = firmwareResponse.getString(
                "firmwareVersion"
            )
        } else {
            runOnUiThread {
                UiUtils().showToast(context, getString(R.string.firmware_not_found))
                UiUtils().showToast(context, getString(R.string.firmware_try_switch_region))
            }
            finish()
        }
        if (firmwareResponse.has("changeLog")) {
            firmwareChangelogTextView.text = StringUtils().getChangelogFixed(
                firmwareResponse.getString("changeLog")
            )
        } else {
            firmwareChangelogLayout.visibility = View.GONE
        }
        if (firmwareResponse.has("lang")) {
            firmwareLanguagesTextView.text = Language().getName(
                firmwareResponse.getString("lang")
            )
        }

        firmwareDownloadButton.setOnClickListener {
            getFirmware(firmwareResponse, context, deviceNameValue)
        }

        firmwareDownloadButton.setOnLongClickListener {
            shareFirmware()
            true
        }
    }

    private fun getFirmware(
        jsonObject: JSONObject,
        context: Context,
        deviceName: String,
    ) {
        for (i in responseFirmwareTagsArray) {
            if (jsonObject.has(i)) {
                val urlString = jsonObject.getString(i)
                DozeRequest().getFirmwareFile(context, urlString, deviceName)
            }
        }
        UiUtils().showToast(context, getString(R.string.firmware_downloading))
    }

    private fun shareFirmware() {
        val firmwareLinks = arrayListOf<String>()

        for (i in responseFirmwareTagsArray) {
            if (firmwareResponse.has(i)) {
                firmwareLinks.add(firmwareResponse.getString(i))
            }
        }

        val shareContent = "$deviceNameValue\n${firmwareLinks.joinToString("\n")}"

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareContent)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, deviceNameValue)
        startActivity(shareIntent)
    }
}