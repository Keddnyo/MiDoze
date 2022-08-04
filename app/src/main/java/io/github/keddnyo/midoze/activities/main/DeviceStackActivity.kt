package io.github.keddnyo.midoze.activities.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.adapters.DeviceStackAdapter
import io.github.keddnyo.midoze.fragments.DeviceFragment
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.remote.Updates
import io.github.keddnyo.midoze.utils.AppVersion
import io.github.keddnyo.midoze.utils.AsyncTask
import java.util.concurrent.Executors

class DeviceStackActivity : AppCompatActivity() {
    private lateinit var deviceListRecyclerView: RecyclerView
    private val context = this@DeviceStackActivity

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_stack)

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)
        val emptyResponse: ConstraintLayout = findViewById(R.id.emptyResponse)
        deviceListRecyclerView = findViewById(R.id.deviceStackRecyclerView)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        deviceListRecyclerView.layoutManager = resources.getBoolean(R.bool.isLeftSidePane).let { left ->
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
                    Updates(context).execute()

                    mainHandler.post {
                        title = getString(R.string.app_name)
                        emptyResponse.visibility = View.GONE
                    }

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

                                    editor.putString(
                                        "deviceStackCache",
                                        gson.toJson(arrayList).toString()
                                    )
                                    editor.apply()
                                }
                            }

                            if (deviceArrayListSlot.isNotEmpty() && deviceArrayListSlot != deviceArrayList) {
                                deviceArrayList = deviceArrayListSlot
                            }
                        }
                    }

                    mainHandler.post {
                        deviceStackAdapter.addDevice(deviceArrayList)

                        if (deviceStackAdapter.itemCount == 0) {
                            emptyResponse.visibility = View.VISIBLE
                        } else {
                            val deviceFragment = DeviceFragment()
                            val args = Bundle()
                            args.putString(
                                "deviceArray",
                                gson.toJson(deviceArrayList[0].deviceStack)
                            )
                            deviceFragment.arguments = args

                            val fm = supportFragmentManager
                            if (!fm.isDestroyed) {
                                fm.beginTransaction()
                                    .replace(R.id.deviceFrame, deviceFragment)
                                    .commit()
                            }

                            title = deviceArrayList[0].name
                        }

                        refreshLayout.isRefreshing = false
                    }
                }
            }
        }

        val versionCode = AppVersion(context).code
        if (prefs.getInt("VERSION_CODE", 0) != versionCode) {
            editor.putInt("VERSION_CODE", versionCode)
            editor.putString("deviceStackCache", "")
            editor.apply()
        }

        refreshLayout.setOnRefreshListener {
            if (Requests().isOnline(context)) {
                editor.putString("deviceStackCache", "")
                editor.apply()
            }
            GetDevices(context).execute()
        }

        GetDevices(context).execute()
        refreshLayout.isRefreshing = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_request -> {
                startActivity(Intent(context, RequestActivity::class.java))
            }
            R.id.action_about -> {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_APP_REPOSITORY))
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}