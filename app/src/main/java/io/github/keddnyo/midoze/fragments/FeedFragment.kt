package io.github.keddnyo.midoze.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
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
import io.github.keddnyo.midoze.utils.TinyDB
import io.github.keddnyo.midoze.utils.UiUtils
import io.github.keddnyo.midoze.utils.devices.DeviceData
import io.github.keddnyo.midoze.utils.firmwares.FirmwaresAdapter
import io.github.keddnyo.midoze.utils.firmwares.FirmwaresData
import org.json.JSONArray
import org.json.JSONObject

class FeedFragment : Fragment() {

    private val deviceListIndex = hashMapOf<String, Int>()
    private val firmwaresAdapter = FirmwaresAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private lateinit var prefs: SharedPreferences
    private var state: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(requireActivity()) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        val firmwaresRefreshLayout: SwipeRefreshLayout = findViewById(R.id.firmwaresRefreshLayout)

        val firmwaresProgressBar: ProgressBar = findViewById(R.id.firmwaresProgressBar)
        val firmwaresLoadingDataText: TextView = findViewById(R.id.firmwaresLoadingDataText)

        val firmwaresErrorMessage: ConstraintLayout = findViewById(R.id.firmwaresErrorMessage)

        deviceListRecyclerView = findViewById(R.id.deviceListRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(this, UiUtils().getGridLayoutIndex(this, 400))

        val adapter = firmwaresAdapter
        deviceListRecyclerView.adapter = adapter

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = prefs.edit()

        class LoadDataForActivity :
            AsyncTask<Void?, Void?, Void>() {

            val tinydb = TinyDB(context)
            val gson = Gson()
            var deviceArrayList: ArrayList<DeviceData> = arrayListOf()

            var firmwaresData: JSONObject = JSONObject("{}")
            val preloadedFirmwares = prefs.getString("Firmwares", "").toString()

            @Deprecated("Deprecated in Java")
            override fun onPreExecute() {
                super.onPreExecute()
                firmwaresProgressBar.visibility = View.VISIBLE
                firmwaresLoadingDataText.visibility = View.VISIBLE
                firmwaresErrorMessage.visibility = View.GONE
                firmwaresRefreshLayout.isRefreshing = false
            }

            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {
                /*val deviceArrayListObject = tinydb.getListObject("deviceArrayList", DeviceData::class.java)

                deviceArrayListObject.forEach {
                    deviceArrayList.add(it as DeviceData)
                }*/


                fun getFirmwaresData() {
                    val isOnline = DozeRequest().isOnline(context)

                    if (isOnline) {
                        deviceArrayList = DozeRequest().getFirmwareLatest(context)

                        val jsArray = gson.toJson(deviceArrayList)
                        editor.putString("deviceArrayList", jsArray.toString())
                        editor.apply()

                        /*val json2 = gson.toJson(deviceArrayList)
                        editor.putString("deviceArrayList", json2)
                        editor.apply()*/

                        /*deviceArrayListObject.clear()
                        deviceArrayList.forEach {
                            deviceArrayListObject.add(it as Any)
                        }

                        tinydb.putListObject("deviceArrayList", deviceArrayListObject)*/
                    } else {
                        runOnUiThread {
                            firmwaresProgressBar.visibility = View.GONE
                            firmwaresErrorMessage.visibility = View.VISIBLE
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

                deviceArrayList.forEachIndexed { index, it ->
                    firmwaresAdapter.addDevice(
                        FirmwaresData(
                            it.name,
                            it.icon,
                            StringUtils().getLocalFirmwareDate(it.buildTime),
                            it.firmware.getString("firmwareVersion").toString(),
                            it.deviceSource.toInt(),
                            it.productionSource.toInt()
                        )
                    )
                    firmwaresAdapter.notifyItemInserted(index)
                    deviceListIndex[it.name] = index
                }

                firmwaresProgressBar.visibility = View.GONE
                firmwaresLoadingDataText.visibility = View.GONE

                /*fun getData(favorite: Boolean) {
                    if (firmwaresData != JSONObject("{}")) {

                        val responseParamsArray = firmwaresData.toMap()
                        val keys = responseParamsArray.keys

                        for (i in keys) {
                            val jsonObject = firmwaresData.getJSONObject(i)

                            val deviceNameValue = jsonObject.getString("name")
                            val deviceIconValue = when {
                                deviceNameValue.contains(getString(R.string.title_mi_band),
                                    true) -> {
                                    R.drawable.ic_xiaomi
                                }
                                deviceNameValue.contains(getString(R.string.title_zepp), true) -> {
                                    R.drawable.ic_zepp
                                }
                                else -> {
                                    R.drawable.ic_amazfit
                                }
                            }
                            val firmwareVersionValue = jsonObject.getString("fw")
                            val firmwareReleaseDateValue =
                                StringUtils().getLocaleFirmwareDate(jsonObject.getString("date"))

                            val firmwareVersion = getString(R.string.firmware_version)
                            val firmwareChangelogValue = "$firmwareVersion: $firmwareVersionValue"

                            if (prefs.getBoolean(i, false) == favorite) {
                                firmwaresAdapter.addDevice(
                                    FirmwaresData(
                                        deviceNameValue,
                                        deviceIconValue,
                                        firmwareReleaseDateValue,
                                        firmwareChangelogValue,
                                        i.toInt()
                                    )
                                )
                                firmwaresAdapter.notifyItemInserted(i.toInt())
                                deviceListIndex[deviceNameValue] = i.toInt()
                            }
                        }
                    }
                }
                firmwaresProgressBar.visibility = View.GONE
                firmwaresRefreshLayout.isRefreshing = false
                getData(true)
                getData(false)*/

                if (state != null) {
                    deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
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

        firmwaresRefreshLayout.setOnRefreshListener {
            deviceListRecyclerView.scrollToPosition(0)
            setData()
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

    private fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { it ->
        when (val value = this[it]) {
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else -> value
        }
    }
}