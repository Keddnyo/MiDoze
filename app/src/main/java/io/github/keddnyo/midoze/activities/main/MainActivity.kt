package io.github.keddnyo.midoze.activities.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
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
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.remote.GetAppUpdate
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val firmwaresAdapter = FirmwaresAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private val context = this@MainActivity
    private var state: Parcelable? = null

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
            GetAppUpdate(context).execute()
        }

        class GetDevices(val context: Context) : AsyncTask() {
            var deviceArrayList: ArrayList<FirmwareData> = arrayListOf()
            override fun execute() {
                if (prefs.getBoolean("allowUpdate", true)) {
                    Executors.newSingleThreadExecutor().execute {
                        mainHandler.post {
                            refreshLayout.isRefreshing = false
                            feedProgressBar.visibility = View.VISIBLE
                            emptyResponse.visibility = View.GONE
                        }

                        var deviceArrayListBackup = prefs.getString("deviceArrayListString", "")

                        if (deviceArrayListBackup != "") {
                            deviceArrayList = GsonBuilder().create().fromJson(
                                deviceArrayListBackup.toString(),
                                object : TypeToken<ArrayList<FirmwareData>>() {}.type
                            )
                        } else if (DozeRequest().isOnline(context)) {
                            DozeRequest().getFirmwareLatest(context).forEach { device ->
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
        }

        GetDevices(context).execute()

        refreshLayout.setOnRefreshListener {
            editor.putBoolean("allowUpdate", false)
            editor.apply()

            GetDevices(context).execute()
        }

        if (state != null) {
            deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
        }
    }

    override fun onPause() {
        super.onPause()
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            state = deviceListRecyclerView.layoutManager?.onSaveInstanceState()
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