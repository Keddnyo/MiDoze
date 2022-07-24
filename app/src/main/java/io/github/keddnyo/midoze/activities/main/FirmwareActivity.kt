package io.github.keddnyo.midoze.activities.main

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
import io.github.keddnyo.midoze.activities.request.ResponseActivity
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.Display
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject

class FirmwareActivity : AppCompatActivity() {

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
        supportActionBar?.setDisplayShowTitleEnabled(false)

        runBlocking {
            init(this@FirmwareActivity)
        }
    }

    private suspend fun init(context: Context) = withContext(Dispatchers.IO) {
        val deviceNameTextView: TextView = findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView = findViewById(R.id.deviceIconImageView)
        val firmwareVersionTextView: TextView = findViewById(R.id.firmwareVersionTextView)
        val firmwareChangelogTextView: TextView = findViewById(R.id.firmwareChangelogTextView)
        val firmwareChangelogLayout: MaterialCardView = findViewById(R.id.firmwareChangelogLayout)
        val firmwareLanguagesTextView: TextView = findViewById(R.id.firmwareLanguagesTextView)
        val firmwareLanguagesLayout: MaterialCardView = findViewById(R.id.firmwareLanguagesLayout)
        val firmwareDownloadButton: Button = findViewById(R.id.firmwareDownloadButton)

        if (Requests().isOnline(context)) {
            runOnUiThread {
                firmwareDownloadButton.visibility = View.VISIBLE
            }
        }

        val deviceIconValue = intent.getIntExtra("deviceIcon", R.drawable.amazfit_bip)
        deviceNameValue = intent.getStringExtra("deviceName").toString()
        firmwareResponse = JSONObject(intent.getStringExtra("firmwareData").toString())
        deviceNameTextView.text = deviceNameValue

        fun openResponseActivity() {
            val intent = Intent(context, ResponseActivity::class.java)
            intent.putExtra("json", firmwareResponse.toString())
            startActivity(intent)
        }

        deviceIconImageView.setImageResource(deviceIconValue)

        firmwareVersionTextView.text = firmwareResponse.getString(
            "firmwareVersion"
        )
        if (firmwareResponse.has("changeLog")) {
            firmwareChangelogTextView.text = StringUtils().getChangelogFixed(
                firmwareResponse.getString("changeLog")
            )
        } else {
            firmwareChangelogLayout.visibility = View.GONE
        }
        if (firmwareResponse.has("lang")) {
            firmwareLanguagesTextView.text = Display().getLanguageName(
                firmwareResponse.getString("lang")
            )
        } else {
            firmwareLanguagesLayout.visibility = View.GONE
        }

        firmwareDownloadButton.setOnClickListener {
            if (Requests().isOnline(context)) {
                getFirmware(firmwareResponse, context, deviceNameValue)
            } else {
                Display().showToast(context, getString(R.string.empty_response))
            }
        }

        firmwareDownloadButton.setOnLongClickListener {
            shareFirmware()
            true
        }

        deviceNameTextView.setOnLongClickListener {
            openResponseActivity()
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
                Requests().getFirmwareFile(context, urlString, deviceName)
            }
        }
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