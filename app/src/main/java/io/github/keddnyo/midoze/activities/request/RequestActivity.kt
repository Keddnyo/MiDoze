package io.github.keddnyo.midoze.activities.request

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.FirmwareActivity
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.Region
import io.github.keddnyo.midoze.local.dataModels.Wearable
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_LIFE_VERSION
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_VERSION
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.Display
import io.github.keddnyo.midoze.utils.PackageUtils
import kotlinx.coroutines.runBlocking

class RequestActivity : AppCompatActivity() {

    private val context = this@RequestActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        title = getString(R.string.menu_request)

        val requestDeviceSourceEditText: TextInputEditText =
            findViewById(R.id.requestDeviceSourceEditText)
        val requestProductionSourceEditText: TextInputEditText =
            findViewById(R.id.requestProductionSourceEditText)
        val requestAppNameEditText: TextInputEditText = findViewById(R.id.requestAppNameEditText)
        val requestAppVersionEditText: TextInputEditText =
            findViewById(R.id.requestAppVersionEditText)
        val requestCountryEditText: TextInputEditText = findViewById(R.id.requestCountryEditText)
        val requestLangEditText: TextInputEditText = findViewById(R.id.requestLangEditText)
        val submitButton: MaterialButton = findViewById(R.id.requestSubmitButton)
        val appButton: MaterialButton = findViewById(R.id.request_app_button)
        val importButton: MaterialButton = findViewById(R.id.requestImportButton)

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
        if (intent.hasExtra("deviceSource")) {
            val deviceSourceValue = getIntentExtra("deviceSource")
            val productionSourceValue = getIntentExtra("productionSource")
            val appNameValue = getIntentExtra("appName")
            val appVersionValue = getIntentExtra("appVersion")
            val countryValue = getIntentExtra("country")
            val langValue = getIntentExtra("lang")

            requestDeviceSourceEditText.setText(deviceSourceValue)
            requestProductionSourceEditText.setText(productionSourceValue)
            requestAppNameEditText.setText(appNameValue)
            requestAppVersionEditText.setText(appVersionValue)
            requestCountryEditText.setText(countryValue)
            requestLangEditText.setText(langValue)
            appButton.visibility = View.GONE
        }

        submitButton.setOnClickListener {
            if (Requests().isOnline(context)) {
                val firmwareResponse = runBlocking {
                    Requests().getFirmwareData(
                        context,
                        wearable = Wearable(
                            requestDeviceSourceEditText.text.toString().trim(),
                            requestProductionSourceEditText.text.toString().trim(),
                            Application(
                                requestAppNameEditText.text.toString().trim(),
                                requestAppVersionEditText.text.toString().trim()
                            ),
                            Region(
                                requestCountryEditText.text.toString().trim(),
                                requestLangEditText.text.toString().trim()
                            )
                        )
                    )
                }

                val intent = Intent(context, FirmwareActivity::class.java)

                if (firmwareResponse != null) {
                    intent.putExtra("deviceName", firmwareResponse.device.name)
                    intent.putExtra("deviceIcon", firmwareResponse.device.image)
                    intent.putExtra("firmwareData", firmwareResponse.firmware.toString())

                    intent.putExtra("productionSource", firmwareResponse.wearable.productionSource)
                    intent.putExtra("deviceSource", firmwareResponse.wearable.deviceSource)
                    intent.putExtra("appName", firmwareResponse.wearable.application.name)
                    intent.putExtra("appVersion", firmwareResponse.wearable.application.version)

                    context.startActivity(intent)
                } else {
                    Display().showToast(context, getString(R.string.empty_response))
                }
            } else {
                Display().showToast(context, getString(R.string.empty_response))
            }
        }

        submitButton.setOnLongClickListener {
            editor.putString("productionSource", requestProductionSourceEditText.text.toString())
            editor.putString("deviceSource", requestDeviceSourceEditText.text.toString())
            editor.putString("appVersion", requestAppVersionEditText.text.toString())
            editor.putString("appname", requestAppNameEditText.text.toString())
            editor.putString("country", requestCountryEditText.text.toString())
            editor.putString("lang", requestLangEditText.text.toString())
            editor.apply()
            importButton.visibility = View.VISIBLE
            true
        }

        fun setZeppAppData() {
            requestAppNameEditText.setText(ZEPP_NAME)
            requestAppVersionEditText.setText(
                PackageUtils(context).getPackageVersion(ZEPP_PACKAGE_NAME) ?: ZEPP_VERSION
            )
        }

        fun setZeppLifeAppData() {
            requestAppNameEditText.setText(ZEPP_LIFE_NAME)
            requestAppVersionEditText.setText(
                PackageUtils(context).getPackageVersion(ZEPP_LIFE_PACKAGE_NAME)
                    ?: ZEPP_LIFE_VERSION
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
            requestProductionSourceEditText.setText(prefs.getString("productionSource", ""))
            requestDeviceSourceEditText.setText(prefs.getString("deviceSource", ""))
            requestAppVersionEditText.setText(prefs.getString("appVersion", ""))
            requestAppNameEditText.setText(prefs.getString("appname", ""))
            requestCountryEditText.setText(prefs.getString("country", ""))
            requestLangEditText.setText(prefs.getString("lang", ""))
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
    
    private fun getIntentExtra(key: String): String {
        return intent.getStringExtra(key).toString()
    }
}