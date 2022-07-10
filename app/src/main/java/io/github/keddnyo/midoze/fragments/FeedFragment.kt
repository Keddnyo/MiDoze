package io.github.keddnyo.midoze.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
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
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.StringUtils
import io.github.keddnyo.midoze.utils.UiUtils
import io.github.keddnyo.midoze.utils.devices.DeviceData
import io.github.keddnyo.midoze.utils.firmwares.FirmwaresAdapter
import io.github.keddnyo.midoze.utils.firmwares.FirmwaresData

class FeedFragment : Fragment() {

    private val deviceListIndex = hashMapOf<String, Int>()
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

        val feedRefreshLayout: SwipeRefreshLayout = findViewById(R.id.firmwaresRefreshLayout)
        val feedProgressBar: ProgressBar = findViewById(R.id.firmwaresProgressBar)
        val feedConnectivityError: ConstraintLayout = findViewById(R.id.feedConnectivityError)
        val feedDevicesNotFound: ConstraintLayout = findViewById(R.id.feedDevicesNotFound)

        deviceListRecyclerView = findViewById(R.id.deviceListRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(this, UiUtils().getGridLayoutIndex(this, 400))

        val adapter = firmwaresAdapter
        deviceListRecyclerView.adapter = adapter

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()

        class LoadDataForActivity :
            AsyncTask<Void?, Void?, Void>() {

            val gson = Gson()
            var deviceArrayList: ArrayList<DeviceData> = arrayListOf()

            var appName = ""
            var appVersion: String? = ""

            @Deprecated("Deprecated in Java")
            override fun onPreExecute() {
                super.onPreExecute()
                feedProgressBar.visibility = View.VISIBLE
                feedConnectivityError.visibility = View.GONE
                feedDevicesNotFound.visibility = View.GONE
                feedRefreshLayout.isRefreshing = false
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {

                fun getAppName(): String? {
                    return prefs.getString("filters_feed_app_name", "Zepp")
                }

                appName = when (getAppName()) {
                    "Zepp" -> {
                        "com.huami.midong"
                    } else -> {
                        "com.xiaomi.hm.health"
                    }
                }

                appVersion = when (getAppName()) {
                    "Zepp" -> {
                        prefs.getString("filters_feed_zepp_app_version", getString(R.string.filters_request_zepp_app_version_value))
                    } else -> {
                        prefs.getString("filters_feed_zepp_app_life_version", getString(R.string.filters_request_zepp_life_app_version_value))
                    }
                }

                fun getFirmwaresData() {
                    val isOnline = DozeRequest().isOnline(context)

                    if (isOnline) {
                        deviceArrayList = DozeRequest().getFirmwareLatest(context, appName, appVersion.toString())

                        val jsArray = gson.toJson(deviceArrayList)
                        editor.putString("deviceArrayList", jsArray.toString())
                        editor.apply()
                    } else {
                        runOnUiThread {
                            feedProgressBar.visibility = View.GONE
                            feedConnectivityError.visibility = View.VISIBLE
                        }
                    }
                }

                if (prefs.getBoolean("settings_feed_ignore_cached_items", true)) {
                    getFirmwaresData()
                } else {
                    val gson = GsonBuilder().create()

                    val json1 = prefs.getString("deviceArrayList", "")

                    if (json1 != "") {
                        deviceArrayList = gson.fromJson(json1.toString(), object :TypeToken<ArrayList<DeviceData>>(){}.type)
                    } else {
                        getFirmwaresData()
                    }
                }

                return null
            }

            @Deprecated("Deprecated in Java")
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                fun getData(favorite: Boolean) {
                    deviceArrayList.forEachIndexed { index, it ->
                        if (prefs.getBoolean(it.deviceSource, false) == favorite) {
                            firmwaresAdapter.addDevice(
                                FirmwaresData(
                                    it.name,
                                    it.icon,
                                    StringUtils().getLocalFirmwareDate(it.buildTime),
                                    it.firmware.getString("firmwareVersion").toString(),
                                    it.deviceSource.toInt(),
                                    it.productionSource.toInt(),
                                    appName,
                                    appVersion.toString()
                                )
                            )
                        }
                        firmwaresAdapter.notifyItemInserted(index)
                        deviceListIndex[it.name] = index
                    }
                }

                getData(true)
                getData(false)
                feedProgressBar.visibility = View.GONE

                if (firmwaresAdapter.itemCount == 0) {
                    feedDevicesNotFound.visibility = View.VISIBLE
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setData() {
            firmwaresAdapter.clear()
            firmwaresAdapter.notifyDataSetChanged()
            LoadDataForActivity().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }

        if (FirmwaresAdapter().itemCount == 0) {
            setData()
        }

        feedRefreshLayout.setOnRefreshListener {
            setData()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_feed, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                firmwaresAdapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firmwaresAdapter.clear()
    }
}