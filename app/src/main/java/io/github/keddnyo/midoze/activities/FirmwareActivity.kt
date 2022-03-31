package io.github.keddnyo.midoze.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    private var firmwareResponse = JSONObject()
    private var deviceNameValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        val firmwareLanguagesTextView: TextView = findViewById(R.id.firmwareLanguagesTextView)
        val firmwareDownloadButton: Button = findViewById(R.id.firmwareDownloadButton)

        deviceNameValue = intent.getStringExtra("deviceName").toString()
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

        firmwareResponse = getFirmwareLinks(firmwareRequest)

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
            makeToast(getString(R.string.firmware_not_found))
            finish()
        }
        firmwareChangelogTextView.text = if (firmwareResponse.has("changeLog")) {
            StringUtils().getChangelogFixed(
                firmwareResponse.getString("changeLog")
            )
        } else {
            getString(R.string.firmware_not_found)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_firmware, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareFirmwareMenuItem -> {
                val shareContent = arrayListOf<String>()

                for (i in firmwareResponseLinksValuesArray) {
                    if (firmwareResponse.has(i)) {
                        shareContent.add(firmwareResponse.getString(i))
                    }
                }

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareContent.joinToString("\n"))
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, deviceNameValue)
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun getFirmwareLinks(request: Request): JSONObject {
        return withContext(Dispatchers.IO) {
            JSONObject(
                OkHttpClient().newCall(request).execute().body()?.string().toString()
            )
        }
    }

    private fun getFirmware(
        jsonObject: JSONObject,
        context: Context,
        deviceName: String,
    ) {
        for (i in firmwareResponseLinksValuesArray) {
            if (jsonObject.has(i)) {
                val urlString = jsonObject.getString(i)
                DozeRequest().getFirmwareFile(context, urlString, deviceName)
            }
        }
        makeToast(getString(R.string.firmware_downloading))
    }

    private fun makeToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}