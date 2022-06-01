package io.github.keddnyo.midoze.activities.request

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.UiUtils

class ResponseActivity : AppCompatActivity() {

    private val context = this@ResponseActivity
    private var json: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.profile_custom_request_server_response)

        UiUtils().switchDarkMode(this)

        if (intent.hasExtra("json")) {
            val responseTextView: TextView = findViewById(R.id.response_text)
            json = StringUtils().getExtrasFixed(intent.getStringExtra("json").toString())
            responseTextView.text = json
            responseTextView.requestFocus()
        } else {
            runOnUiThread {
                UiUtils().showToast(context, getString(R.string.firmware_not_found))
            }
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_firmware, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share_firmware -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, json)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, getString(R.string.profile_other))
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}