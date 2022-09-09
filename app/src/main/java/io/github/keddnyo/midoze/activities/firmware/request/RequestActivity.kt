package io.github.keddnyo.midoze.activities.firmware.request

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.FirmwarePreviewActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.local.repositories.packages.PackageNames.ZEPP_LIFE_NAME
import io.github.keddnyo.midoze.local.repositories.packages.PackageNames.ZEPP_LIFE_PACKAGE_NAME
import io.github.keddnyo.midoze.local.repositories.packages.PackageNames.ZEPP_NAME
import io.github.keddnyo.midoze.local.repositories.packages.PackageNames.ZEPP_PACKAGE_NAME
import io.github.keddnyo.midoze.local.repositories.packages.PackageVersions.ZEPP_LIFE_VERSION
import io.github.keddnyo.midoze.local.repositories.packages.PackageVersions.ZEPP_VERSION
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.getPackageVersion
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class RequestActivity : AppCompatActivity() {

    private val context = this@RequestActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        title = getString(R.string.menu_request)

        val requestDeviceSourceEditText: TextInputEditText =
            findViewById(R.id.requestDeviceSourceEditText)
        val requestProductionSourceEditText: TextInputEditText =
            findViewById(R.id.requestProductionSourceEditText)
        val requestAppNameEditText: TextInputEditText =
            findViewById(R.id.requestAppNameEditText)
        val requestAppVersionEditText: TextInputEditText =
            findViewById(R.id.requestAppVersionEditText)
        val requestCountryEditText: TextInputEditText =
            findViewById(R.id.requestCountryEditText)
        val requestLangEditText: TextInputEditText =
            findViewById(R.id.requestLangEditText)
        val submitButton: Button =
            findViewById(R.id.requestSubmitButton)
        val appButton: Button =
            findViewById(R.id.request_app_button)
        val importButton: Button =
            findViewById(R.id.requestImportButton)

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
        if (intent.hasExtra("firmware")) {
            importButton.visibility = View.VISIBLE

            val device: FirmwareData.Firmware = GsonBuilder().create().fromJson(
                intent.getStringExtra("firmware").toString(),
                object : TypeToken<FirmwareData.Firmware>() {}.type
            )

            val deviceSourceValue = device.wearable.deviceSource
            val productionSourceValue = device.wearable.productionSource
            val appNameValue = device.wearable.application.name
            val appVersionValue = device.wearable.application.version
            val countryValue = device.wearable.region.country
            val langValue = device.wearable.region.language

            requestDeviceSourceEditText.setText(deviceSourceValue)
            requestProductionSourceEditText.setText(productionSourceValue)
            requestAppNameEditText.setText(appNameValue)
            requestAppVersionEditText.setText(appVersionValue)
            requestCountryEditText.setText(countryValue)
            requestLangEditText.setText(langValue)
        }

        submitButton.setOnClickListener {
            if (OnlineStatus().isOnline(context)) {
                val firmwareDataResponse = runBlocking(Dispatchers.IO) {
                    fun TextView.getCode() = this.text.toString().trim()

                    Requests().getFirmwareData(
                        FirmwareData.Wearable(
                            requestDeviceSourceEditText.getCode(),
                            requestProductionSourceEditText.getCode(),
                            FirmwareData.Application(
                                requestAppNameEditText.getCode(),
                                requestAppVersionEditText.getCode()
                            ),
                            FirmwareData.Region(
                                requestCountryEditText.getCode(),
                                requestLangEditText.getCode()
                            )
                        )
                    )
                }

                if (firmwareDataResponse != null) {
                    startActivity(
                        Intent(this, FirmwarePreviewActivity::class.java).apply {
                            putExtra(
                                "firmwareArray", Gson().toJson(
                                    arrayListOf(
                                        firmwareDataResponse
                                    )
                                ).toString()
                            )
                        }
                    )
                } else {
                    getString(R.string.empty_response).showAsToast(context)
                }
            } else {
                getString(R.string.connectivity_error).showAsToast(context)
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
                ZEPP_PACKAGE_NAME.getPackageVersion(context)
                    ?: ZEPP_VERSION
            )
        }

        fun setZeppLifeAppData() {
            requestAppNameEditText.setText(ZEPP_LIFE_NAME)
            requestAppVersionEditText.setText(
                ZEPP_LIFE_PACKAGE_NAME.getPackageVersion(context)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}