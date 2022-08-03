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
import io.github.keddnyo.midoze.utils.Display
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

        deviceListRecyclerView.layoutManager = resources.getBoolean(R.bool.isLeftSidePane).let {
            if (it) {
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

                    prefs.getString("deviceArrayListString", "").let { deviceStackCache ->
                        if (deviceStackCache != "") {
                            deviceArrayList = GsonBuilder().create().fromJson(
                                deviceStackCache.toString(),
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
                                        "deviceArrayListString",
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

                    editor.putBoolean("allowUpdate", true)
                    editor.apply()
                }
            }
        }

        val versionCode = AppVersion(context).code
        if (prefs.getInt("VERSION_CODE", 0) != versionCode) {
            editor.putInt("VERSION_CODE", versionCode)
            editor.putString("deviceArrayListString", "")
            editor.apply()
        }

        GetDevices(context).execute()
        refreshLayout.isRefreshing  = true

        refreshLayout.setOnRefreshListener {
            if (prefs.getBoolean("allowUpdate", true) && Requests().isOnline(context)) {
                if (deviceStackAdapter.itemCount != 0) {
                    Display().showToast(this, getString(R.string.background_update))
                }

                editor.putString("deviceArrayListString", "")
                editor.putBoolean("allowUpdate", false)
                editor.apply()
            }

            GetDevices(context).execute()
        }
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