package io.github.keddnyo.midoze.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.UiUtils
import io.github.keddnyo.midoze.utils.deviceList.DeviceListAdapter
import io.github.keddnyo.midoze.utils.deviceList.DeviceListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val deviceListIndex = hashMapOf<String, Int>()
    private val deviceListAdapter = DeviceListAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private lateinit var prefs: SharedPreferences
    private var state: Parcelable? = null
    private val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            setContentView(R.layout.activity_main)
            deviceListRecyclerView = findViewById(R.id.device_list_recycler_view)
            prefs = PreferenceManager.getDefaultSharedPreferences(this)

            UiUtils().switchDarkMode(this)

            if (DozeRequest().isOnline(this)) {
                getData(true)
                getData(false)
            } else {
                title = getString(R.string.error_title)
            }

            if (state != null) {
                deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
            }

            updateChecker()
        } else {
            finish()
            startActivity(Intent(this, ExtrasRequestActivity::class.java))
            UiUtils().showToast(context, getString(R.string.compatibility_mode))
        }
    }

    private fun getData(favorite: Boolean) {
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(this, UiUtils().getRecyclerSpanCount(this))

        val adapter = deviceListAdapter
        deviceListRecyclerView.adapter = adapter

        val deviceListJson = runBlocking {
            withContext(Dispatchers.IO) {
                DozeRequest().getFirmwareLatest()
            }
        }
        val responseParamsArray = deviceListJson.toMap()
        val keys = responseParamsArray.keys

        for (i in keys) {
            val jsonObject = deviceListJson.getJSONObject(i)

            val deviceNameValue = jsonObject.getString("name")
            val deviceIconValue = when {
                deviceNameValue.contains(getString(R.string.title_mi_band), true) -> {
                    R.drawable.ic_xiaomi
                }
                deviceNameValue.contains(getString(R.string.title_zepp), true) -> {
                    R.drawable.ic_zepp
                }
                else -> {
                    R.drawable.ic_amazfit
                }
            }
            val firmwareVersionValue = jsonObject.getString("fw")
            val firmwareReleaseDateValue =
                StringUtils().getLocaleFirmwareDate(jsonObject.getString("date"))

            val firmwareVersion = getString(R.string.firmware_version)
            val firmwareChangelogValue = "$firmwareVersion: $firmwareVersionValue"

            if (prefs.getBoolean(i, false) == favorite) {
                deviceListAdapter.addDevice(
                    DeviceListData(
                        deviceNameValue,
                        deviceIconValue,
                        firmwareReleaseDateValue,
                        firmwareChangelogValue,
                        i.toInt()
                    )
                )
                deviceListAdapter.notifyItemInserted(i.toInt())
                deviceListIndex[deviceNameValue] = i.toInt()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            state = deviceListRecyclerView.layoutManager?.onSaveInstanceState()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                deviceListAdapter.filter.filter(newText)
                return false
            }
        })

        menu.findItem(R.id.action_settings).setOnMenuItemClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        return true
    }

    private fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { it ->
        when (val value = this[it]) {
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else -> value
        }
    }

    private fun updateChecker() {
        if (prefs.getBoolean("settings_app_update_checker", true) && DozeRequest().isOnline(this)) {
            val latestReleaseInfoJson = runBlocking {
                withContext(Dispatchers.IO) {
                    DozeRequest().getApplicationLatestReleaseInfo(context)
                }
            }

            if (latestReleaseInfoJson.has("tag_name") && latestReleaseInfoJson.getJSONArray("assets").toString() != "[]") {
                val latestVersion = latestReleaseInfoJson.getString("tag_name")
                val latestVersionLink =
                    latestReleaseInfoJson.getJSONArray("assets").getJSONObject(0)
                        .getString("browser_download_url")

                if (BuildConfig.VERSION_NAME < latestVersion) {
                    val builder = AlertDialog.Builder(context)
                        .setTitle(getString(R.string.update_dialog_title))
                        .setMessage(getString(R.string.update_dialog_message))
                        .setIcon(R.drawable.ic_info)
                        .setCancelable(false)
                    builder.setPositiveButton(R.string.update_dialog_button) { _: DialogInterface?, _: Int ->
                        DozeRequest().getFirmwareFile(context,
                            latestVersionLink,
                            getString(R.string.app_name))
                        UiUtils().showToast(context, getString(R.string.downloading_toast))
                        DialogInterface.BUTTON_POSITIVE
                    }
                    builder.setNegativeButton(android.R.string.cancel) { _: DialogInterface?, _: Int ->
                        DialogInterface.BUTTON_NEGATIVE
                    }
                    builder.show()
                }
            }
        }
    }
}