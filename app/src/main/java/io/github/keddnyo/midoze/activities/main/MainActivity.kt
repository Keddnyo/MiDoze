package io.github.keddnyo.midoze.activities.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
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
import io.github.keddnyo.midoze.remote.AppUpdates
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.utils.Display

class MainActivity : AppCompatActivity() {
    private val firmwaresAdapter = FirmwaresAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private lateinit var prefs: SharedPreferences
    private var state: Parcelable? = null

    val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            setContentView(R.layout.activity_main)

            if (DozeRequest().isOnline(context)) {
                AppUpdates(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }

            init()
        } else {
            finish()
            startActivity(Intent(this, RequestActivity::class.java))
            Display().showToast(context, getString(R.string.compatibility_mode))
        }
    }

    private fun init() {
        val firmwaresRefreshLayout: SwipeRefreshLayout = findViewById(R.id.firmwaresRefreshLayout)
        val feedProgressBar: ProgressBar = findViewById(R.id.firmwaresProgressBar)
        val feedConnectivityError: ConstraintLayout = findViewById(R.id.feedConnectivityError)
        val feedDevicesNotFound: ConstraintLayout = findViewById(R.id.feedDevicesNotFound)

        deviceListRecyclerView = findViewById(R.id.deviceListRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(
                this, Display()
                    .getGridLayoutIndex(this, 200)
            )

        val adapter = firmwaresAdapter
        deviceListRecyclerView.adapter = adapter

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()

        fun isOnline(): Boolean {
            return DozeRequest().isOnline(context)
        }

        class FetchFirmwareData :
            AsyncTask<Void?, Void?, Void>() {

            val gson = Gson()
            var deviceArrayList: ArrayList<FirmwareData> = arrayListOf()

            @Deprecated("Deprecated in Java")
            override fun onPreExecute() {
                super.onPreExecute()
                firmwaresRefreshLayout.isRefreshing = false
                feedProgressBar.visibility = View.VISIBLE
                feedConnectivityError.visibility = View.GONE
                feedDevicesNotFound.visibility = View.GONE
                editor.putBoolean("allow_exit", false).apply()
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {

                val commonDeviceArrayListJson = prefs.getString("deviceArray", "")

                if (commonDeviceArrayListJson != "") {
                    deviceArrayList = GsonBuilder().create().fromJson(
                        commonDeviceArrayListJson.toString(),
                        object : TypeToken<ArrayList<FirmwareData>>() {}.type
                    )
                } else {
                    if (isOnline()) {
                        val zeppDeviceArrayList =
                            DozeRequest().getFirmwareLatest(context)

                        for (i in zeppDeviceArrayList) {
                            deviceArrayList.add(i)
                        }

                        val commonDeviceArray = gson.toJson(deviceArrayList)
                        editor.putString("deviceArray", commonDeviceArray.toString())
                        editor.apply()
                    } else {
                        runOnUiThread {
                            feedProgressBar.visibility = View.GONE
                            feedConnectivityError.visibility = View.VISIBLE
                        }
                    }
                }

                return null
            }

            @Deprecated("Deprecated in Java")
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                firmwaresAdapter.addDevice(deviceArrayList)

                deviceArrayList.forEachIndexed { index, _ ->
                    firmwaresAdapter.notifyItemInserted(index)
                }

                feedProgressBar.visibility = View.GONE

                if (isOnline() && firmwaresAdapter.itemCount == 0) {
                    feedDevicesNotFound.visibility = View.VISIBLE
                }

                editor.putBoolean("allow_exit", true).apply()
            }
        }

        fun setData() {
            val itemCount = firmwaresAdapter.itemCount
            firmwaresAdapter.clear()
            firmwaresAdapter.notifyItemRangeRemoved(0, itemCount)
            FetchFirmwareData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }

        firmwaresRefreshLayout.setOnRefreshListener {
            if (isOnline() && firmwaresAdapter.itemCount != 0) {
                val prefs: SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context)

                prefs.edit().putString("deviceArray", "").apply()
            }

            setData()
        }

        setData()

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
        }
        return super.onOptionsItemSelected(item)
    }
}