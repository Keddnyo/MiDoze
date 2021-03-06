package io.github.keddnyo.midoze.activities.request

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.FirmwareActivity
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.Region
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_LIFE_VERSION
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_VERSION
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.Display
import io.github.keddnyo.midoze.utils.PackageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class RequestActivity : AppCompatActivity() {

    private val context = this@RequestActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        title = getString(R.string.menu_custom_request)

        val extrasDeviceSourceEditText: TextInputEditText =
            findViewById(R.id.extrasDeviceSourceEditText)
        val extrasProductionSourceEditText: TextInputEditText =
            findViewById(R.id.extrasProductionSourceEditText)
        val extrasAppNameEditText: TextInputEditText = findViewById(R.id.extrasAppNameEditText)
        val extrasAppVersionEditText: TextInputEditText =
            findViewById(R.id.extrasAppVersionEditText)
        val extrasCountryEditText: TextInputEditText = findViewById(R.id.extrasCountryEditText)
        val extrasLangEditText: TextInputEditText = findViewById(R.id.extrasLangEditText)
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
            val deviceSourceValue = intent.getStringExtra("deviceSource").toString()
            val productionSourceValue = intent.getStringExtra("productionSource").toString()
            val appNameValue = intent.getStringExtra("appName").toString()
            val appVersionValue = intent.getStringExtra("appVersion").toString()
            val countryValue = intent.getStringExtra("country").toString()
            val langValue = intent.getStringExtra("lang").toString()

            extrasDeviceSourceEditText.setText(deviceSourceValue)
            extrasProductionSourceEditText.setText(productionSourceValue)
            extrasAppNameEditText.setText(appNameValue)
            extrasAppVersionEditText.setText(appVersionValue)
            extrasCountryEditText.setText(countryValue)
            extrasLangEditText.setText(langValue)
        }

        submitButton.setOnClickListener {
            val isOnline = runBlocking(Dispatchers.Default) {
                Requests().isOnline(context)
            }

            if (isOnline) {
                val firmwareResponse = runBlocking {
                    Requests().getFirmwareData(
                        context,
                        extrasDeviceSourceEditText.text.toString().trim(),
                        extrasProductionSourceEditText.text.toString().trim(),
                        Application(
                            extrasAppNameEditText.text.toString().trim(),
                            extrasAppVersionEditText.text.toString().trim()
                        ),
                        Region(
                            extrasCountryEditText.text.toString().trim(),
                            extrasLangEditText.text.toString().trim()
                        )
                    )
                }

                val intent = Intent(context, FirmwareActivity::class.java)

                if (firmwareResponse != null) {
                    intent.putExtra("deviceName", firmwareResponse.device.name)
                    intent.putExtra("deviceIcon", firmwareResponse.device.image)
                    intent.putExtra("firmwareData", firmwareResponse.firmware.toString())

                    intent.putExtra("productionSource", firmwareResponse.productionSource)
                    intent.putExtra("deviceSource", firmwareResponse.deviceSource)
                    intent.putExtra("appName", firmwareResponse.application.name)
                    intent.putExtra("appVersion", firmwareResponse.application.version)

                    context.startActivity(intent)
                } else {
                    Display().showToast(context, getString(R.string.empty_response))
                }
            } else {
                Display().showToast(context, getString(R.string.empty_response))
            }
        }

        submitButton.setOnLongClickListener {
            editor.putString("productionSource", extrasProductionSourceEditText.text.toString())
            editor.putString("deviceSource", extrasDeviceSourceEditText.text.toString())
            editor.putString("appVersion", extrasAppVersionEditText.text.toString())
            editor.putString("appname", extrasAppNameEditText.text.toString())
            editor.putString("country", extrasCountryEditText.text.toString())
            editor.putString("lang", extrasLangEditText.text.toString())
            editor.apply()
            importButton.visibility = View.VISIBLE
            true
        }

        fun setZeppAppData() {
            extrasAppNameEditText.setText(ZEPP_NAME)
            extrasAppVersionEditText.setText(
                PackageUtils().getPackageVersion(context, ZEPP_PACKAGE_NAME) ?: ZEPP_VERSION
            )
        }

        fun setZeppLifeAppData() {
            extrasAppNameEditText.setText(ZEPP_LIFE_NAME)
            extrasAppVersionEditText.setText(
                PackageUtils().getPackageVersion(context, ZEPP_LIFE_PACKAGE_NAME) ?: ZEPP_LIFE_VERSION
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
            extrasCountryEditText.setText(prefs.getString("country", ""))
            extrasLangEditText.setText(prefs.getString("lang", ""))
        }

        importButton.setOnLongClickListener {
            editor.remove("productionSource")
            editor.remove("deviceSource")
            editor.remove("appVersion")
            editor.remove("appname")
            editor.remove("country")
            editor.remove("lang")
            editor.apply()
            importButton.visibility = View.GONE
            true
        }
    }
}