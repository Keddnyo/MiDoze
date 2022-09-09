package io.github.keddnyo.midoze.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import io.github.keddnyo.midoze.adapters.firmware.FirmwareAdapter
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.local.menu.Dimens
import io.github.keddnyo.midoze.local.menu.Dimens.SWIPE_LAYOUT_REFRESH_LENGTH
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.PackageUtils
import java.util.concurrent.Executors

class FirmwareFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_firmware,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(requireActivity()) {
        super.onViewCreated(view, savedInstanceState)

        val refreshFirmwareLayout: SwipeRefreshLayout = findViewById(R.id.refreshFirmwareLayout)
        val firmwareEmptyResponse: ConstraintLayout = findViewById(R.id.firmwareEmptyResponse)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        refreshFirmwareLayout.setDistanceToTriggerSync(SWIPE_LAYOUT_REFRESH_LENGTH)

        OnlineStatus(this).run {
            class GetDevices(val context: Context) : AsyncTask() {
                var deviceArrayList: ArrayList<FirmwareData.FirmwareDataArray> = arrayListOf()

                override fun execute() {
                    Executors.newSingleThreadExecutor().execute {
                        prefs.getString("deviceStackCache", "").toString().let { deviceStackCache ->
                            if (deviceStackCache.isNotBlank() && deviceStackCache != "null") {
                                deviceArrayList = GsonBuilder().create().fromJson(
                                    deviceStackCache,
                                    object : TypeToken<ArrayList<FirmwareData.FirmwareDataArray>>() {}.type
                                )
                            } else if (isOnline()) {
                                deviceArrayList = Requests().getFirmwareLatest(context)

                                editor.apply {
                                    putString(
                                        "deviceStackCache",
                                        gson.toJson(deviceArrayList).toString()
                                    )
                                    apply()
                                }
                            }
                        }

                        mainHandler.post {
                            FirmwareAdapter(deviceArrayList).let { adapter ->
                                if (adapter.itemCount == 0) {
                                    firmwareEmptyResponse.visibility = View.VISIBLE
                                } else {
                                    firmwareEmptyResponse.visibility = View.GONE
                                }

                                findViewById<RecyclerView>(R.id.deviceStackRecyclerView).apply {
                                    layoutManager =
                                        GridLayoutManager(
                                            requireActivity(), Display()
                                                .getGridLayoutIndex(requireActivity(), Dimens.CARD_GRID_WIDTH)
                                        )
                                    this.adapter = adapter
                                }
                            }

                            refreshFirmwareLayout.isRefreshing = false
                        }
                    }
                }
            }

            PackageUtils(context, context.packageName).getPackageVersionBuild().let {
                if (prefs.getInt("VERSION_CODE", 0) != it) {
                    editor.apply {
                        if (it != null) {
                            putInt("VERSION_CODE", it)
                            remove("deviceStackCache")
                            apply()
                        }
                    }
                }
            }

            GetDevices(context).run {
                refreshFirmwareLayout.setOnRefreshListener {
                    if (isOnline()) {
                        editor.run {
                            remove("deviceStackCache")
                            apply()
                        }
                    }

                    execute()
                }

                execute()
                refreshFirmwareLayout.isRefreshing = true
            }
        }
    }
}