package io.github.keddnyo.midoze.activities.request

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.FirmwareActivity
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_LIFE_VERSION
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_VERSION
import io.github.keddnyo.midoze.utils.Display
import io.github.keddnyo.midoze.utils.PackageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class RequestActivity : AppCompatActivity() {

    private val context = this@RequestActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        title = getString(R.string.settings_custom_request)

        Display().switchDarkMode(this)

        val extrasDeviceSourceEditText: TextInputEditText =
            findViewById(R.id.extrasDeviceSourceEditText)
        val extrasProductionSourceEditText: TextInputEditText =
            findViewById(R.id.extrasProductionSourceEditText)
        val extrasAppNameEditText: TextInputEditText = findViewById(R.id.extrasAppNameEditText)
        val extrasAppVersionEditText: TextInputEditText =
            findViewById(R.id.extrasAppVersionEditText)
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
        if (intent.getStringExtra("deviceSource")?.isNotBlank() == true) {
            val deviceName = intent.getStringExtra("deviceName").toString()
            val deviceSourceValue = intent.getStringExtra("deviceSource").toString()
            val productionSourceValue = intent.getStringExtra("productionSource").toString()
            val appNameValue = intent.getStringExtra("appName").toString()
            val appVersionValue = intent.getStringExtra("appVersion").toString()

            title = deviceName
            extrasDeviceSourceEditText.setText(deviceSourceValue)
            extrasDeviceSourceEditText.isEnabled = false
            extrasProductionSourceEditText.setText(productionSourceValue)
            extrasProductionSourceEditText.isEnabled = false
            extrasAppNameEditText.setText(appNameValue)
            extrasAppNameEditText.isEnabled = false
            extrasAppVersionEditText.setText(appVersionValue)

            importButton.visibility = View.GONE
            appButton.visibility = View.GONE
        }

        val isOnline = runBlocking(Dispatchers.IO) {
            DozeRequest().getHostReachable() != null
        }

        submitButton.setOnClickListener {
            if (isOnline) {
                val firmwareResponse = runBlocking {
                    DozeRequest().getFirmwareData(
                        context,
                        extrasDeviceSourceEditText.text.toString().trim(),
                        extrasProductionSourceEditText.text.toString().trim(),
                        Application(
                            extrasAppNameEditText.text.toString().trim(),
                            extrasAppVersionEditText.text.toString().trim()
                        ),
                    )
                }

                val intent = Intent(context, FirmwareActivity::class.java)

                if (firmwareResponse != null) {
                    intent.putExtra("deviceName", firmwareResponse.wearable.name)
                    intent.putExtra("deviceIcon", firmwareResponse.wearable.image)
                    intent.putExtra("firmwareData", firmwareResponse.firmware.toString())

                    intent.putExtra("productionSource", firmwareResponse.productionSource)
                    intent.putExtra("deviceSource", firmwareResponse.deviceSource)
                    intent.putExtra("appName", firmwareResponse.application.name)
                    intent.putExtra("appVersion", firmwareResponse.application.version)

                    context.startActivity(intent)
                } else {
                    Display().showToast(context, getString(R.string.feed_devices_not_found))
                }



//                val intent = Intent(context, ResponseActivity::class.java)
//                intent.putExtra("json", firmwareResponse.toString())
//                startActivity(intent)
            } else {
                Display().showToast(context, getString(R.string.feed_connectivity_error))
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

        fun setZeppAppData() {
            extrasAppNameEditText.setText(ZEPP_NAME)
            extrasAppVersionEditText.setText(
                PackageUtils().getPackageVersion(context, ZEPP_NAME) ?: ZEPP_VERSION
            )
        }

        fun setZeppLifeAppData() {
            extrasAppNameEditText.setText(ZEPP_LIFE_NAME)
            extrasAppVersionEditText.setText(
                PackageUtils().getPackageVersion(context, ZEPP_LIFE_NAME) ?: ZEPP_LIFE_VERSION
            )
        }

        appButton.setOnClickListener {
            if (prefs.getString("filters_app_name", "Zepp") == "Zepp") {
                setZeppAppData()
            } else {
                setZeppLifeAppData()
            }
        }

        appButton.setOnLongClickListener {
            if (prefs.getString("filters_app_name", "Zepp") == "Zepp") {
                setZeppLifeAppData()
            } else {
                setZeppAppData()
            }
            true
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