package io.github.keddnyo.midoze.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.AppVersion
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import java.util.concurrent.Executors

class DeviceStackFragment : Fragment() {
    private val deviceStackAdapter = DeviceStackAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_stack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(view) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)

        val feedProgressBar: ProgressBar = findViewById(R.id.firmwaresProgressBar)
        val emptyResponse: ConstraintLayout = findViewById(R.id.emptyResponse)
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()

        deviceListRecyclerView = findViewById(R.id.deviceListRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(
                context, Display()
                    .getGridLayoutIndex(context, 400)
            )

        val adapter = deviceStackAdapter
        deviceListRecyclerView.adapter = adapter

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
}