package io.github.keddnyo.midoze.activities.firmware

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.DeviceStackAdapter
import io.github.keddnyo.midoze.fragments.DeviceContainer
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.AppVersion
import io.github.keddnyo.midoze.utils.AsyncTask
import java.util.concurrent.Executors

class DeviceStackActivity : AppCompatActivity() {
    private val context = this@DeviceStackActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_stack)

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)
        val emptyResponse: ConstraintLayout = findViewById(R.id.emptyResponse)
        val deviceListRecyclerView: RecyclerView = findViewById(R.id.deviceStackRecyclerView)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        deviceListRecyclerView.layoutManager =
            resources.getBoolean(R.bool.isLeftSidePane).let { left ->
                if (left) {
                    LinearLayoutManager.VERTICAL
                } else {
                    LinearLayoutManager.HORIZONTAL
                }.let { orientation ->
                    LinearLayoutManager(this, orientation, false)
                }
            }

        val deviceStackAdapter = DeviceStackAdapter()
        deviceListRecyclerView.adapter = deviceStackAdapter

        class GetDevices(val context: Context) : AsyncTask() {
            var deviceArrayList: ArrayList<FirmwareDataStack> = arrayListOf()
            var deviceArrayListSlot: ArrayList<FirmwareDataStack> = arrayListOf()

            override fun execute() {
                Executors.newSingleThreadExecutor().execute {
                    prefs.getString("deviceStackCache", "").toString().let { deviceStackCache ->
                        if (deviceStackCache.isNotEmpty()) {
                            deviceArrayList = GsonBuilder().create().fromJson(
                                deviceStackCache,
                                object : TypeToken<ArrayList<FirmwareDataStack>>() {}.type
                            )
                        } else if (Requests().isOnline(context)) {
                            Requests().getFirmwareLatest(context).forEach { device ->
                                if (deviceStackAdapter.itemCount == 0) {
                                    deviceArrayList
                                } else {
                                    deviceArrayListSlot
                                }.let { arrayList ->
                                    arrayList.add(device)

                                    editor.apply {
                                        putString(
                                            "deviceStackCache",
                                            gson.toJson(arrayList).toString()
                                        )
                                        apply()
                                    }
                                }
                            }

                            deviceArrayListSlot.let {
                                if (it.isNotEmpty() && it != deviceArrayList) {
                                    deviceArrayList = it
                                }
                            }
                        }
                    }

                    mainHandler.post {
                        deviceStackAdapter.addDevice(deviceArrayList)

                        if (deviceStackAdapter.itemCount == 0) {
                            emptyResponse.visibility = View.VISIBLE
                        } else {
                            emptyResponse.visibility = View.GONE
                            DeviceContainer().show(this@DeviceStackActivity, deviceArrayList, 0)
                        }

                        refreshLayout.isRefreshing = false
                    }
                }
            }
        }

        AppVersion(context).code.let {
            if (prefs.getInt("VERSION_CODE", 0) != it) {
                editor.apply {
                    putInt("VERSION_CODE", it)
                    remove("deviceStackCache")
                    apply()
                }
            }
        }

        GetDevices(context).run {
            refreshLayout.setOnRefreshListener {
                if (Requests().isOnline(context)) {
                    editor.apply {
                        remove("deviceStackCache")
                        apply()
                    }
                }

                execute()
            }

            execute()
            refreshLayout.isRefreshing = true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}