package io.github.keddnyo.midoze.activities.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.remote.Updates
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val firmwaresAdapter = FirmwaresAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private val context = this@MainActivity

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    .getGridLayoutIndex(this, 200)
            )

        val adapter = firmwaresAdapter
        deviceListRecyclerView.adapter = adapter

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Updates(context).execute()
        }

        class GetDevices(val context: Context) : AsyncTask() {
            var deviceArrayList: ArrayList<FirmwareData> = arrayListOf()
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
                            object : TypeToken<ArrayList<FirmwareData>>() {}.type
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
                        firmwaresAdapter.addDevice(deviceArrayList)
                        deviceArrayList.forEachIndexed { index, _ ->
                            firmwaresAdapter.notifyItemInserted(index)
                        }

                        feedProgressBar.visibility = View.GONE

                        if (firmwaresAdapter.itemCount == 0) {
                            emptyResponse.visibility = View.VISIBLE
                        }
                    }

                    editor.putBoolean("allowUpdate", true)
                    editor.apply()
                }
            }
        }

        val versionCode = BuildConfig.VERSION_CODE
        if (prefs.getInt("VERSION_CODE", 0) != versionCode) {
            GetDevices(context).execute()

            editor.putInt("VERSION_CODE", versionCode)
            editor.apply()
        }

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false

            if (prefs.getBoolean("allowUpdate", true) && Requests().isOnline(context)) {
                firmwaresAdapter.clear()
                firmwaresAdapter.notifyDataSetChanged()

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
}