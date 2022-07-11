package io.github.keddnyo.midoze.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.utils.Language
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.Display
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

        runBlocking {
            withContext(Dispatchers.IO) {
                init(this@FirmwareActivity)
            }
        }
    }

    private suspend fun init(context: Context) = withContext(Dispatchers.IO) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val deviceNameTextView: TextView = findViewById(R.id.deviceNameTextView)
        val deviceIconTextView: ImageView = findViewById(R.id.deviceIconImageView)
        val firmwareVersionTextView: TextView = findViewById(R.id.firmwareVersionTextView)
        val firmwareChangelogTextView: TextView = findViewById(R.id.firmwareChangelogTextView)
        val firmwareChangelogLayout: MaterialCardView = findViewById(R.id.firmwareChangelogLayout)
        val firmwareLanguagesTextView: TextView = findViewById(R.id.firmwareLanguagesTextView)
        val firmwareLanguagesLayout: MaterialCardView = findViewById(R.id.firmwareLanguagesLayout)
        val firmwareDownloadButton: Button = findViewById(R.id.firmwareDownloadButton)

        deviceNameValue = intent.getStringExtra("deviceName").toString()

        val isShowChangelog = prefs.getBoolean("settings_firmwares_show_changelog", false)
        val isShowLanguages = prefs.getBoolean("settings_firmwares_show_languages", true)

        firmwareResponse = JSONObject(intent.getStringExtra("firmwareData").toString())

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

        firmwareVersionTextView.text = firmwareResponse.getString(
            "firmwareVersion"
        )
        if (isShowChangelog && firmwareResponse.has("changeLog")) {
            firmwareChangelogTextView.text = StringUtils().getChangelogFixed(
                firmwareResponse.getString("changeLog")
            )
        } else {
            firmwareChangelogLayout.visibility = View.GONE
        }
        if (isShowLanguages && firmwareResponse.has("lang")) {
            firmwareLanguagesTextView.text = Language().getName(
                firmwareResponse.getString("lang")
            )
        } else {
            firmwareLanguagesLayout.visibility = View.GONE
        }

        firmwareDownloadButton.setOnClickListener {
            if (DozeRequest().isOnline(context)) {
                getFirmware(firmwareResponse, context, deviceNameValue)
            } else {
                Display().showToast(context, getString(R.string.feed_connectivity_error))
            }
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
        Display().showToast(context, getString(R.string.downloading_toast))
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