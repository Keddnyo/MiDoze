package io.github.keddnyo.midoze.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.Language
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.UiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

        firmwareResponse = DozeRequest().getFirmwareLinks(
            productionSourceValue,
            deviceSourceValue,
            appVersionValue,
            appNameValue
        )

        title = deviceNameValue
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
            }
            finish()
        }
        firmwareChangelogTextView.text = if (firmwareResponse.has("changeLog")) {
            StringUtils().getChangelogFixed(
                firmwareResponse.getString("changeLog")
            )
        } else {
            getString(R.string.changelog_not_specified)
        }
        if (firmwareResponse.has("lang")) {
            firmwareLanguagesTextView.text = Language().getName(
                firmwareResponse.getString("lang")
            )
        }

        firmwareDownloadButton.setOnClickListener {
            getFirmware(firmwareResponse, context, deviceNameValue)

            val prefs: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()

            var counter = prefs.getInt("downloadCount", 0)
            counter++

            editor.putInt("downloadCount", counter)
            editor.apply()
        }

        firmwareDownloadButton.setOnLongClickListener {
            DozeRequest().getOldVersions(context, deviceSourceValue)
            return@setOnLongClickListener true
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
            R.id.action_share_firmware -> {
                shareFirmware()
            }
        }
        return super.onOptionsItemSelected(item)
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
        UiUtils().showToast(context, getString(R.string.firmware_downloading))
    }

    private fun shareFirmware() {
        val firmwareLinks = arrayListOf<String>()

        for (i in firmwareResponseLinksValuesArray) {
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

        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

        var counter = prefs.getInt("shareCount", 0)
        counter++

        editor.putInt("shareCount", counter)
        editor.apply()
    }
}