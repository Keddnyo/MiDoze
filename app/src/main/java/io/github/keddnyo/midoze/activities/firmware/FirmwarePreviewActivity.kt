package io.github.keddnyo.midoze.activities.firmware

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.ResponseActivity
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.toLanguageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class FirmwarePreviewActivity : AppCompatActivity() {
    private var firmwareResponse = JSONObject()

    private val responseFirmwareTagsArray = arrayOf(
        "firmwareUrl",
        "resourceUrl",
        "baseResourceUrl",
        "fontUrl",
        "gpsUrl"
    )

    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        val context = this@FirmwarePreviewActivity

        fun openResponseActivity() {
            val intent = Intent(context, ResponseActivity::class.java)
            intent.putExtra("json", firmwareResponse.toString())
            startActivity(intent)
        }

        val deviceIconValue = intent.getIntExtra("preview", R.drawable.amazfit_bip)
        firmwareResponse = JSONObject(intent.getStringExtra("data").toString())

        if (intent.hasExtra("firmwareVersion")) {
            title = intent.getStringExtra("title").toString().trim()
            val subtitle = firmwareResponse.getString("firmwareVersion")

            supportActionBar?.title = title
            supportActionBar?.subtitle = subtitle

            val preview: ImageView =
                findViewById(R.id.preview)
            val description: TextView =
                findViewById(R.id.description)
            val download: ExtendedFloatingActionButton =
                findViewById(R.id.download)

            preview.setImageResource(
                deviceIconValue
            )

            if (firmwareResponse.has("changeLog")) {
                description.text = firmwareResponse.getString("lang").toLanguageList()
            } else {
                description.visibility = View.GONE
            }

            if (OnlineStatus(context).isOnline) {
                download.isEnabled = true
                download.setOnClickListener {
                    runBlocking(Dispatchers.IO) {
                        for (i in responseFirmwareTagsArray) {
                            if (firmwareResponse.has(i)) {
                                val urlString = firmwareResponse.getString(i)
                                Requests().getFirmwareFile(
                                    context,
                                    urlString,
                                    title,
                                    getString(R.string.menu_firmwares)
                                )
                            }
                        }
                    }
                }
            }

            preview.setOnLongClickListener {
                openResponseActivity()
                true
            }
        } else {
            finish()
        }
    }

    private fun shareContent() {
        val firmwareLinks = arrayListOf<String>()

        for (i in responseFirmwareTagsArray) {
            if (firmwareResponse.has(i)) {
                firmwareLinks.add(firmwareResponse.getString(i))
            }
        }

        val shareContent = StringBuilder().append(title)
            .append("\n")
            .append(firmwareLinks.joinToString("\n"))
            .append("\n")
            .append(getString(R.string.firmware_share_get_it_on, getString(R.string.app_name)))
            .append("\n")
            .append(GITHUB_APP_REPOSITORY)
            .toString()

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareContent)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, title)
        startActivity(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_response, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share_firmware -> {
                shareContent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}