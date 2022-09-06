package io.github.keddnyo.midoze.activities.firmware

import android.content.Context
import android.os.Bundle
import android.view.View
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
import io.github.keddnyo.midoze.adapters.firmware.DeviceStackAdapter
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.local.menu.Dimens
import io.github.keddnyo.midoze.remote.Requests
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.PackageUtils
import java.util.concurrent.Executors

class DeviceStackActivity : AppCompatActivity() {
    private val context = this@DeviceStackActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.menu_firmwares)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmwares)

        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshFirmwareLayout)
        val emptyResponse: ConstraintLayout = findViewById(R.id.emptyResponse)
        val deviceListRecyclerView: RecyclerView = findViewById(R.id.deviceStackRecyclerView)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()
        val gson = Gson()

        OnlineStatus(context).run {
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
                            } else if (isOnline) {
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
                            DeviceStackAdapter(deviceArrayList).let { adapter ->
                                if (adapter.itemCount == 0) {
                                    emptyResponse.visibility = View.VISIBLE
                                } else {
                                    emptyResponse.visibility = View.GONE
                                }

                                deviceListRecyclerView.apply {
                                    layoutManager =
                                        GridLayoutManager(
                                            context, Display()
                                                .getGridLayoutIndex(context, Dimens.CARD_GRID_WIDTH)
                                        )
                                    this.adapter = adapter
                                }
                            }

                            refreshLayout.isRefreshing = false
                        }
                    }
                }
            }

            PackageUtils(context, context.packageName).getPackageVersionBuild().let {
                if (prefs.getInt("VERSION_CODE", 0) != it) {
                    editor.apply {
                        putInt("VERSION_CODE", it)
                        remove("deviceStackCache")
                        apply()
                    }
                }
            }

            GetDevices(context).run {
                refreshLayout.setOnRefreshListener {
                    if (isOnline) {
                        editor.apply {
                            remove("deviceStackCache")
                            apply()
                        }
                    }

                    execute()
                }

                execute()
                refreshLayout.isRefreshing = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}