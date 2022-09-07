package io.github.keddnyo.midoze.activities.firmware.request

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.StringUtils.showAsToast
import io.github.keddnyo.midoze.utils.StringUtils.toServerResponse

class ResponseActivity : AppCompatActivity() {

    private val context = this@ResponseActivity
    private var json: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.settings_server_response)

        if (intent.hasExtra("json")) {
            val responseTextView: TextView = findViewById(R.id.response_text)
            json = intent.getStringExtra("json").toString().toServerResponse()
            responseTextView.text = json
            responseTextView.requestFocus()
        } else {
            runOnUiThread {
                getString(R.string.empty_response).showAsToast(context)
            }
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, json)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, getString(R.string.settings_server_response))
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