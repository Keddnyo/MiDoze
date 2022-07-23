package io.github.keddnyo.midoze.activities.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.remote.GetAppUpdate
import io.github.keddnyo.midoze.remote.Routes
import io.github.keddnyo.midoze.remote.Routes.GITHUB_APP_REPOSITORY
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import org.json.JSONObject
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val firmwaresAdapter = FirmwaresAdapter()

    private lateinit var deviceListRecyclerView: RecyclerView

    private val context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            GetAppUpdate(context).execute()
        }

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

        class GetDevices(val context: Context) : AsyncTask() {
            val deviceArrayList: ArrayList<FirmwareData> = arrayListOf()
            override fun execute() {
                Executors.newSingleThreadExecutor().execute {
                    if (DozeRequest().isOnline(context)) {
                        DozeRequest().getFirmwareLatest(context).forEach { device ->
                            deviceArrayList.add(device)
                        }
                    }

                    mainHandler.post {
                        firmwaresAdapter.addDevice(deviceArrayList)
                        deviceArrayList.forEachIndexed { index, _ ->
                            firmwaresAdapter.notifyItemInserted(index)
                        }

                        if (firmwaresAdapter.itemCount == 0){
                            emptyResponse.visibility = View.VISIBLE
                        }

                        feedProgressBar.visibility = View.GONE
                    }
                }
            }
        }

        GetDevices(context).execute()
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