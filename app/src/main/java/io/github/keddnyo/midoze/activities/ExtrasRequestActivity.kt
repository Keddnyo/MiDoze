package io.github.keddnyo.midoze.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import kotlinx.coroutines.runBlocking

class ExtrasRequestActivity : AppCompatActivity() {

    private val context = this@ExtrasRequestActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extras_request)
        title = getString(R.string.settings_custom_request)

        val extrasDeviceSourceEditText: EditText = findViewById(R.id.extrasDeviceSourceEditText)
        val extrasProductionSourceEditText: EditText =
            findViewById(R.id.extrasProductionSourceEditText)
        val extrasAppNameEditText: EditText = findViewById(R.id.extrasAppNameEditText)
        val extrasAppVersionEditText: EditText = findViewById(R.id.extrasAppVersionEditText)
        val submitButton: Button = findViewById(R.id.extrasSubmitButton)
        val importButton: Button = findViewById(R.id.extrasImportButton)

        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
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

        submitButton.setOnClickListener {
            val firmwareResponse =
                runBlocking {
                    DozeRequest().getFirmwareLinks(
                        extrasProductionSourceEditText.text.toString(),
                        extrasDeviceSourceEditText.text.toString(),
                        extrasAppVersionEditText.text.toString(),
                        extrasAppNameEditText.text.toString()
                    )
                }

            val intent = Intent(context, ExtrasResponseActivity::class.java)
            intent.putExtra("json", firmwareResponse.toString())
            startActivity(intent)
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