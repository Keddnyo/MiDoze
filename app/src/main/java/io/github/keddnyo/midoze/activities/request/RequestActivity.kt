package io.github.keddnyo.midoze.activities.request

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.UiUtils
import kotlinx.coroutines.runBlocking

class RequestActivity : AppCompatActivity() {

    private val context = this@RequestActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        title = getString(R.string.settings_custom_request)

        UiUtils().switchDarkMode(this)

        val extrasDeviceSourceEditText: TextInputEditText = findViewById(R.id.extrasDeviceSourceEditText)
        val extrasProductionSourceEditText: TextInputEditText =
            findViewById(R.id.extrasProductionSourceEditText)
        val extrasAppNameEditText: TextInputEditText = findViewById(R.id.extrasAppNameEditText)
        val extrasAppVersionEditText: TextInputEditText = findViewById(R.id.extrasAppVersionEditText)
        val submitButton: MaterialButton = findViewById(R.id.extrasSubmitButton)
        val appButton: MaterialButton = findViewById(R.id.extras_app_button)
        val importButton: MaterialButton = findViewById(R.id.extrasImportButton)

        val prefs =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()

        if ((prefs.getString(
                "productionSource",
                ""
            ) != "") || (prefs.getString(
                "deviceSource",
                ""
            ) != "") || (prefs.getString(
                "appVersion",
                ""
            ) != "")
        ) {
            importButton.visibility = View.VISIBLE
        } else {
            importButton.visibility = View.GONE
        }

        // Get Intent
        if (intent.getIntExtra("deviceSource", 0) != 0) {
            val deviceSourceValue = intent.getIntExtra("deviceSource", 0).toString()
            val productionSourceValue = intent.getIntExtra("productionSource", 0).toString()
            val appNameValue = intent.getStringExtra("appName").toString()
            val appVersionValue = intent.getStringExtra("appVersion").toString()

            extrasDeviceSourceEditText.setText(deviceSourceValue)
            extrasProductionSourceEditText.setText(productionSourceValue)
            extrasAppNameEditText.setText(appNameValue)
            extrasAppVersionEditText.setText(appVersionValue)
        }

        submitButton.setOnClickListener {
            if (DozeRequest().isOnline(context)) {
                val firmwareResponse =
                    runBlocking {
                        DozeRequest().getFirmwareData(
                            extrasProductionSourceEditText.text.toString(),
                            extrasDeviceSourceEditText.text.toString(),
                            extrasAppVersionEditText.text.toString(),
                            extrasAppNameEditText.text.toString(),
                            context
                        )
                    }

                val intent = Intent(context, ResponseActivity::class.java)
                intent.putExtra("json", firmwareResponse.toString())
                startActivity(intent)
            } else {
                UiUtils().showToast(context, getString(R.string.firmware_connectivity_error))
            }
        }

        submitButton.setOnLongClickListener {
            editor.putString("productionSource", extrasProductionSourceEditText.text.toString())
            editor.putString("deviceSource", extrasDeviceSourceEditText.text.toString())
            editor.putString("appVersion", extrasAppVersionEditText.text.toString())
            editor.putString("appname", extrasAppNameEditText.text.toString())
            editor.apply()
            importButton.visibility = View.VISIBLE
            true
        }

        appButton.setOnClickListener {
            val zeppAppVersionDefault = getString(R.string.filters_request_zepp_app_version_value)
            val zeppLifeAppVersionDefault = getString(R.string.filters_request_zepp_app_version_value)

            if (prefs.getString("filters_feed_app_name", "Zepp") == "Zepp") {
                extrasAppNameEditText.setText(getString(R.string.zepp_app_name_value))
                extrasAppVersionEditText.setText(
                    prefs.getString("filters_feed_zepp_app_version", zeppAppVersionDefault)
                        ?: zeppAppVersionDefault
                )
            } else {
                extrasAppNameEditText.setText(getString(R.string.zepp_life_app_name_value))
                extrasAppVersionEditText.setText(
                    prefs.getString("filters_feed_zepp_life_app_version", zeppLifeAppVersionDefault)
                        ?: zeppLifeAppVersionDefault
                )
            }
        }

        importButton.setOnClickListener {
            extrasProductionSourceEditText.setText(prefs.getString("productionSource", ""))
            extrasDeviceSourceEditText.setText(prefs.getString("deviceSource", ""))
            extrasAppVersionEditText.setText(prefs.getString("appVersion", ""))
            extrasAppNameEditText.setText(prefs.getString("appname", ""))
        }

        importButton.setOnLongClickListener {
            editor.remove("productionSource")
            editor.remove("deviceSource")
            editor.remove("appVersion")
            editor.remove("appname")
            editor.apply()
            importButton.visibility = View.GONE
            true
        }
    }
}