package io.github.keddnyo.midoze.activities.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.adapters.DeviceStackAdapter
import io.github.keddnyo.midoze.activities.request.RequestActivity
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
    private val deviceStackAdapter = DeviceStackAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private val context = this@DeviceStackActivity
    private var state: Parcelable? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_stack)

        val deviceFrame: FrameLayout = findViewById(R.id.deviceFrame)
        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)

        val feedProgressBar: ProgressBar = findViewById(R.id.firmwaresProgressBar)
        val emptyResponse: ConstraintLayout = findViewById(R.id.emptyResponse)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        deviceListRecyclerView = findViewById(R.id.deviceListRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(
                this, Display()
                    .getGridLayoutIndex(this, 400)
            )

        val adapter = deviceStackAdapter
        deviceListRecyclerView.adapter = adapter

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Updates(context).execute()
        }

        class GetDevices(val context: Context) : AsyncTask() {
            var deviceArrayList: ArrayList<FirmwareDataStack> = arrayListOf()
            override fun execute() {
                Executors.newSingleThreadExecutor().execute {
                    mainHandler.post {
                        feedProgressBar.visibility = View.VISIBLE
                        emptyResponse.visibility = View.GONE
                    }

                    var deviceArrayListBackup = prefs.getString("deviceArrayListString", "")

                    if (deviceArrayListBackup != "") {
                        deviceArrayList = GsonBuilder().create().fromJson(
                            deviceArrayListBackup.toString(),
                            object : TypeToken<ArrayList<FirmwareDataStack>>() {}.type
                        )
                    } else if (Requests().isOnline(context)) {
                        Requests().getFirmwareLatest(context).forEach { device ->
                            deviceArrayList.add(device)
                        }

                        deviceArrayListBackup = gson.toJson(deviceArrayList)
                        editor.putString(
                            "deviceArrayListString",
                            deviceArrayListBackup.toString()
                        )
                        editor.apply()
                    }

                    mainHandler.post {
                        deviceStackAdapter.addDevice(deviceArrayList)
                        deviceArrayList.forEachIndexed { index, _ ->
                            deviceStackAdapter.notifyItemInserted(index)
                        }

                        feedProgressBar.visibility = View.GONE

                        if (deviceStackAdapter.itemCount == 0) {
                            emptyResponse.visibility = View.VISIBLE
                        }

                        if (state != null) {
                            deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
                        }

                        if (deviceArrayList.isNotEmpty()) {
                            val deviceFragment = DeviceFragment()
                            val args = Bundle()
                            args.putString(
                                "deviceArray",
                                gson.toJson(deviceArrayList[0].deviceStack)
                            )
                            deviceFragment.arguments = args

                            supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.deviceFrame, deviceFragment)
                                .commit()
                        }
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

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false

            if (prefs.getBoolean("allowUpdate", true) && Requests().isOnline(context)) {
                deviceStackAdapter.clear()
                deviceStackAdapter.notifyDataSetChanged()

                editor.putString("deviceArrayListString", "")
                editor.putBoolean("allowUpdate", false)
                editor.apply()

                GetDevices(context).execute()
            }
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

    override fun onPause() {
        super.onPause()
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            state = deviceListRecyclerView.layoutManager?.onSaveInstanceState()
        }
    }
}