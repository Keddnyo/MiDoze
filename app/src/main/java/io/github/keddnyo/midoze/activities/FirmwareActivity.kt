package io.github.keddnyo.midoze.activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.Language
import io.github.keddnyo.midoze.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class FirmwareActivity : AppCompatActivity() {

    private val firmwareResponseLinksValuesArray = arrayOf(
        "firmwareUrl",
        "resourceUrl",
        "baseResourceUrl",
        "fontUrl",
        "gpsUrl"
    )

    private val context = this@FirmwareActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware)

        val deviceNameTextView: TextView = findViewById(R.id.deviceNameTextView)
        val deviceIconTextView: ImageView = findViewById(R.id.deviceIconImageView)
        val firmwareVersionTextView: TextView = findViewById(R.id.firmwareVersionTextView)
        val firmwareChangelogTextView: TextView = findViewById(R.id.firmwareChangelogTextView)
        val firmwareLanguagesTextView: TextView = findViewById(R.id.firmwareLanguagesTextView)
        val firmwareDownloadButton: Button = findViewById(R.id.firmwareDownloadButton)

        val deviceNameValue = intent.getStringExtra("deviceName").toString()
        val deviceSourceValue = intent.getIntExtra("deviceSource", 0).toString()
        val productionSourceValue = intent.getStringExtra("productionSource").toString()
        val appNameValue = intent.getStringExtra("appname").toString()
        val appVersionValue = intent.getStringExtra("appVersion").toString()

        val firmwareRequest = DozeRequest().getFirmwareLinks(
            productionSourceValue,
            deviceSourceValue,
            appVersionValue,
            appNameValue
        )

        val firmwareResponse = runBlocking {
            getFirmwareLinks(firmwareRequest)
        }

        deviceNameTextView.text = deviceNameValue

        when {
            deviceNameValue.contains("Mi Band", true) -> {
                deviceIconTextView.setImageResource(R.drawable.ic_xiaomi)
            }
            deviceNameValue.contains("Zepp", true) -> {
                deviceIconTextView.setImageResource(R.drawable.ic_zepp)
            }
            deviceNameValue.contains("Amazfit", true) -> {
                deviceIconTextView.setImageResource(R.drawable.ic_amazfit)
            }
            else -> {
                deviceIconTextView.setImageResource(R.mipmap.ic_launcher_round)
            }
        }

        if (firmwareResponse.has("firmwareVersion")) {
            firmwareVersionTextView.text = firmwareResponse.getString(
                "firmwareVersion"
            )
        }
        if (firmwareResponse.has("changeLog")) {
            firmwareChangelogTextView.text = StringUtils().getChangelogFixed(
                firmwareResponse.getString("changeLog")
            )
        }
        if (firmwareResponse.has("lang")) {
            firmwareLanguagesTextView.text = Language().getName(
                firmwareResponse.getString("lang")
            )
        }

        firmwareDownloadButton.setOnClickListener {
            getFirmware(firmwareResponse, context, deviceNameValue)
        }
    }

    private fun getFirmware(
        jsonObject: JSONObject,
        context: Context,
        deviceName: String
    ) {
        for (i in firmwareResponseLinksValuesArray) {
            if (jsonObject.has(i)) {
                val urlString = jsonObject.getString(i)
                DozeRequest().getFirmwareFile(context, urlString, deviceName)
            }
        }
        Toast.makeText(context, getString(R.string.firmware_downloading_toast), Toast.LENGTH_SHORT).show()
    }

    private suspend fun getFirmwareLinks(request: Request): JSONObject {
        return withContext(Dispatchers.IO) {
            JSONObject(
                OkHttpClient().newCall(request).execute().body()?.string().toString()
            )
        }
    }
}