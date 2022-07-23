package io.github.keddnyo.midoze.activities.main

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.remote.AppUpdates
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.Display

class MainActivity : AppCompatActivity() {
    private val firmwaresAdapter = FirmwaresAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView

    val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            AppUpdates(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
        init()
    }

    private fun init() {
        val firmwaresRefreshLayout: SwipeRefreshLayout = findViewById(R.id.firmwaresRefreshLayout)
        val feedProgressBar: ProgressBar = findViewById(R.id.firmwaresProgressBar)
        val emptyResponse: ConstraintLayout = findViewById(R.id.emptyResponse)

        deviceListRecyclerView = findViewById(R.id.deviceListRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(
                this, Display()
                    .getGridLayoutIndex(this, 200)
            )

        val adapter = firmwaresAdapter
        deviceListRecyclerView.adapter = adapter

        fun isOnline(): Boolean {
            return DozeRequest().isOnline(context)
        }

        class FetchFirmwareData :
            AsyncTask<Void?, Void?, Void>() {

            var deviceArrayList: ArrayList<FirmwareData> = arrayListOf()

            @Deprecated("Deprecated in Java")
            override fun onPreExecute() {
                super.onPreExecute()
                firmwaresRefreshLayout.isRefreshing = false
                feedProgressBar.visibility = View.VISIBLE
                emptyResponse.visibility = View.GONE
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {
                if (isOnline()) {
                    val zeppDeviceArrayList =
                        DozeRequest().getFirmwareLatest(context)

                    for (i in zeppDeviceArrayList) {
                        deviceArrayList.add(i)
                    }
                } else {
                    runOnUiThread {
                        feedProgressBar.visibility = View.GONE
                        emptyResponse.visibility = View.VISIBLE
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
                    emptyResponse.visibility = View.VISIBLE
                }
            }
        }

        fun setData() {
            val itemCount = firmwaresAdapter.itemCount
            firmwaresAdapter.clear()
            firmwaresAdapter.notifyItemRangeRemoved(0, itemCount)
            FetchFirmwareData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }

        firmwaresRefreshLayout.setOnRefreshListener {
            setData()
        }

        setData()
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