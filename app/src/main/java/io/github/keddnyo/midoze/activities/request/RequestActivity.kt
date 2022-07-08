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
        val importButton: MaterialButton = findViewById(R.id.extrasImportButton)

        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()

        if ((sharedPreferences.getString(
                "productionSource",
                ""
            ) != "") || (sharedPreferences.getString(
                "deviceSource",
                ""
            ) != "") || (sharedPreferences.getString(
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

        importButton.setOnClickListener {
            extrasProductionSourceEditText.setText(sharedPreferences.getString("productionSource", ""))
            extrasDeviceSourceEditText.setText(sharedPreferences.getString("deviceSource", ""))
            extrasAppVersionEditText.setText(sharedPreferences.getString("appVersion", ""))
            extrasAppNameEditText.setText(sharedPreferences.getString("appname", ""))
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