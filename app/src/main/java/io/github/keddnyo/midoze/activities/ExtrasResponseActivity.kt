package io.github.keddnyo.midoze.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.UiUtils

class ExtrasResponseActivity : AppCompatActivity() {

    private val context = this@ExtrasResponseActivity
    private var json: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extras_response)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.extras)

        if (intent.hasExtra("json")) {
            val responseTextView: TextView = findViewById(R.id.responseTextView)
            json = StringUtils().getExtrasFixed(intent.getStringExtra("json").toString())
            responseTextView.text = json
        } else {
            runOnUiThread {
                UiUtils().makeToast(context, getString(R.string.firmware_not_found))
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
            R.id.shareFirmwareMenuItem -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, json)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, getString(R.string.extras))
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