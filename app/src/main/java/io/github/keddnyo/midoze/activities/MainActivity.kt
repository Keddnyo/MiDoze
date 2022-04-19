package io.github.keddnyo.midoze.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.UiUtils
import io.github.keddnyo.midoze.utils.deviceList.DeviceListAdapter
import io.github.keddnyo.midoze.utils.deviceList.DeviceListData
import io.github.keddnyo.midoze.utils.deviceList.RecyclerItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val context = this@MainActivity
    private val deviceListIndex = hashMapOf<String, Int>()
    private val deviceListAdapter = DeviceListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        title = getString(R.string.feed)

        deviceListIndex.clear()
        deviceListAdapter.clear()

        val deviceNamesArray = arrayOf(
            getString(R.string.title_amazfit),
            getString(R.string.title_mi_band),
            getString(R.string.title_zepp)
        )

        val deviceIconArray = arrayOf(
            R.drawable.ic_amazfit,
            R.drawable.ic_xiaomi,
            R.drawable.ic_zepp
        )

        val deviceListRecyclerView: RecyclerView = findViewById(R.id.deviceListRecyclerView)

        deviceListRecyclerView.layoutManager =
            GridLayoutManager(context, UiUtils().getRecyclerSpanCount(context))
        deviceListRecyclerView.adapter = deviceListAdapter

        when (DozeRequest().isOnline(context)) {
            true -> {
                val deviceListJson = runBlocking {
                    withContext(Dispatchers.IO) {
                        getDeviceFirmwareLatestJson()
                    }
                }
                val responseParamsArray = deviceListJson.toMap()
                val keys = responseParamsArray.keys

                for (i in keys) {
                    val jsonObject = deviceListJson.getJSONObject(i)

                    val deviceNameValue = jsonObject.getString("name")
                    val deviceIconValue = when {
                        deviceNameValue.contains(deviceNamesArray[1], true) -> {
                            deviceIconArray[1]
                        }
                        deviceNameValue.contains(deviceNamesArray[2], true) -> {
                            deviceIconArray[2]
                        }
                        else -> {
                            deviceIconArray[0]
                        }
                    }
                    val firmwareVersionValue = jsonObject.getString("fw")
                    val firmwareReleaseDateValue = jsonObject.getString("date")

                    val firmwareUpdated = getString(R.string.firmware_updated)
                    val firmwareChangelogValue = "$firmwareUpdated: $firmwareVersionValue"

                    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

                    if (prefs.getBoolean("show_favourites", false)) {
                        if (prefs.getBoolean(i, false)) {
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
                    } else {
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

                /*deviceListRecyclerView.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        deviceListRecyclerView,
                        object : RecyclerItemClickListener.OnItemClickListener {
                            override fun onItemClick(view: View, position: Int) {
                                val deviceName =
                                    (deviceListRecyclerView.adapter as DeviceListAdapter).getDeviceName(
                                        position)
                                when (DozeRequest().isOnline(context)) {
                                    true -> {
                                        deviceListIndex[deviceName]?.let { openFirmwareActivity(it) }
                                    }
                                    false -> {
                                        title = getString(R.string.firmware_connectivity_error)
                                        showConnectivityErrorDialog(context)
                                    }
                                }

                            }
                        })
                )*/
            }
            false -> {
                title = getString(R.string.firmware_connectivity_error)
                showConnectivityErrorDialog(context)
            }
        }
    }

    private suspend fun getDeviceFirmwareLatestJson(): JSONObject {
        return withContext(Dispatchers.IO) {
            JSONObject(DozeRequest().getFirmwareLatest())
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView

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
            R.id.action_fav -> {
                val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = prefs.edit()

                if (!prefs.getBoolean("show_favourites", false)) {
                    editor.putBoolean("show_favourites", true)
                    editor.apply()
                    init()
                } else {
                    editor.putBoolean("show_favourites", false)
                    editor.apply()
                    init()
                }
            }
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showConnectivityErrorDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle(R.string.firmware_connectivity_error)
            .setMessage(R.string.firmware_connectivity_error_message)
            .setIcon(R.drawable.ic_info)
            .setCancelable(false)

        builder.setNegativeButton(R.string.firmware_connectivity_error_refresh) { _: DialogInterface?, _: Int ->
            builder.show().dismiss()
            init()
        }
        builder.setPositiveButton(R.string.firmware_connectivity_error_close) { _: DialogInterface?, _: Int ->
            builder.show().dismiss()
            finish()
        }
        builder.show()
    }
}