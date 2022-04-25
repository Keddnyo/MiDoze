package io.github.keddnyo.midoze.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var state: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UiUtils().switchDarkMode(this)

        if (DozeRequest().isOnline(this)) {
            getData(true)
            getData(false)
        }

        if (state != null) {
            deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
        }
    }

    private fun getData(favorite: Boolean) {
        deviceListRecyclerView = findViewById(R.id.device_list_recycler_view)
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

            val prefs: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this)

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

        state = deviceListRecyclerView.layoutManager?.onSaveInstanceState()
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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
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
}