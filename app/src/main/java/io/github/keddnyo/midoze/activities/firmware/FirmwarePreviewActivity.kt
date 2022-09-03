package io.github.keddnyo.midoze.activities.firmware

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.request.ResponseActivity
import io.github.keddnyo.midoze.adapters.FirmwarePreviewAdapter
import io.github.keddnyo.midoze.local.dataModels.Firmware
import io.github.keddnyo.midoze.local.devices.DeviceRepository
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.toLanguageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class FirmwarePreviewActivity : AppCompatActivity() {
    private lateinit var shareTitle: String
    private var downloadContent = JSONObject()

    private val responseFirmwareTagsArray = arrayOf(
        "firmwareUrl",
        "resourceUrl",
        "baseResourceUrl",
        "fontUrl",
        "gpsUrl"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_preview)

        val context = this@FirmwarePreviewActivity

        if (intent.hasExtra("deviceArray")) {
            val position = intent.getIntExtra("position", 0)

            val deviceArray: ArrayList<Firmware.FirmwareData> = GsonBuilder().create().fromJson(
                intent.getStringExtra("deviceArray").toString(),
                object : TypeToken<ArrayList<Firmware.FirmwareData>>() {}.type
            )

            val description: TextView =
                findViewById(R.id.description)
            val download: ExtendedFloatingActionButton =
                findViewById(R.id.download)

            val viewPager: ViewPager2 = findViewById(R.id.firmwarePreviewPager)
            val adapter = FirmwarePreviewAdapter(deviceArray)
            viewPager.adapter = adapter

            viewPager.setCurrentItem(position, false)
            fun initializeViewPager(position: Int) {
                val device = deviceArray[position]
                val deviceRepository = DeviceRepository().getDeviceNameByCode(device.wearable.deviceSource.toInt())

                shareTitle = deviceRepository.name
                supportActionBar?.title = shareTitle
                downloadContent = JSONObject(device.firmwareData.toString())

                description.apply {
                    if (device.firmwareData.has("lang")) {
                        text = device.firmwareData.getString("lang").toLanguageList()
                    } else {
                        visibility = View.GONE
                    }
                }

                if (OnlineStatus(context).isOnline) {
                    download.apply {
                        isEnabled = true
                        text = device.firmwareData.getString("firmwareVersion")
                        setOnClickListener {
                            runBlocking(Dispatchers.IO) {
                                for (i in responseFirmwareTagsArray) {
                                    if (downloadContent.has(i)) {
                                        val urlString = downloadContent.getString(i)
                                        Requests().getFirmwareFile(
                                            context,
                                            urlString,
                                            deviceRepository.name,
                                            getString(R.string.menu_firmwares)
                                        )
                                    }
                                }
                            }
                        }
                        setOnLongClickListener {
                            Intent(context, ResponseActivity::class.java).let { intent->
                                intent.putExtra("json", downloadContent.toString())
                                context.startActivity(intent)
                            }
                            true
                        }
                    }
                }
            }

            initializeViewPager(position)

            viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    initializeViewPager(position)
                }
            })
        } else {
            finish()
        }
    }

    private fun shareContent() {
        val firmwareLinks = arrayListOf<String>()

        for (i in responseFirmwareTagsArray) {
            if (downloadContent.has(i)) {
                firmwareLinks.add(downloadContent.getString(i))
            }
        }

        val shareContent = StringBuilder().append(shareTitle)
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

        val shareIntent = Intent.createChooser(sendIntent, shareTitle)
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