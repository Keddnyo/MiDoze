package io.github.keddnyo.midoze.fragments

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.ProgressBar
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
import io.github.keddnyo.midoze.local.dataModels.Application
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.remote.DozeRequest
import io.github.keddnyo.midoze.utils.Display

class FeedFragment : Fragment() {

    private val firmwaresAdapter = FirmwaresAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private lateinit var prefs: SharedPreferences
    private var state: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(requireActivity()) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

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
                    fun getAppData(zeppLife: Boolean): Application {
                        return if (zeppLife) {
                            Application(
                                "com.xiaomi.hm.health",
                                prefs.getString(
                                    "filters_zepp_app_life_version",
                                    getString(R.string.filters_request_zepp_life_app_version_value)
                                ).toString()
                            )
                        } else {
                            Application(
                                "com.huami.midong",
                                prefs.getString(
                                    "filters_zepp_app_version",
                                    getString(R.string.filters_request_zepp_app_version_value)
                                ).toString()
                            )
                        }
                    }

                    if (isOnline()) {
                        val zeppDeviceArrayList =
                            DozeRequest().getFirmwareLatest(context, getAppData(false))
                        val zeppLifeDeviceArrayList =
                            DozeRequest().getFirmwareLatest(context, getAppData(true))

                        for (i in zeppLifeDeviceArrayList) {
                            deviceArrayList.add(i)
                        }
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

                if (DozeRequest().isOnline(context) && firmwaresAdapter.itemCount == 0) {
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
            if (firmwaresAdapter.itemCount != 0) {
                val prefs: SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context)

                val host = prefs.getString("filters_request_host", "1").toString()
                val region = prefs.getString("filters_request_region", "1").toString()
                val isAdvancedSearch = prefs.getBoolean("filters_advanced_search", false)
                val zeppVersion = prefs.getString(
                    "filters_zepp_app_version",
                    getString(R.string.filters_request_zepp_app_version_value)
                ).toString()
                val zeppLifeVersion = prefs.getString(
                    "filters_zepp_life_app_version",
                    getString(R.string.filters_request_zepp_life_app_version_value)
                ).toString()

                val request = firmwaresAdapter.getItems()[0].request

                if (!(request.host == host && request.region == region && request.isAdvancedSearch == isAdvancedSearch && request.zeppVersion == zeppVersion && request.zeppLifeVersion == zeppLifeVersion)) {
                    prefs.edit().putString("deviceArray", "").apply()
                }
            }

            if (prefs.getBoolean("allow_exit", true)) {
                setData()
            } else {
                Display().showToast(context, getString(R.string.feed_wait_for_process))
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        firmwaresAdapter.clear()
    }
}