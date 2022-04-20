package io.github.keddnyo.midoze.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.UiUtils
import io.github.keddnyo.midoze.utils.deviceList.DeviceListAdapter
import io.github.keddnyo.midoze.utils.deviceList.DeviceListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class FeedFragment : Fragment() {

    private val deviceListIndex = hashMapOf<String, Int>()
    private val deviceListAdapter = DeviceListAdapter()
    private lateinit var deviceListRecyclerView: RecyclerView
    private var state: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onResume() = with(requireActivity()) {
        super.onResume()

        deviceListIndex.clear()
        deviceListAdapter.clear()

        val deviceNamesArray = arrayOf(
            getString(R.string.title_amazfit),
            getString(R.string.title_mi_band),
            getString(R.string.title_zepp)
        )

        val deviceIconArray = arrayOf(
            R.drawable.ic_amazfit,
            R.drawable.ic_xiaomi,
            R.drawable.ic_zepp
        )

        if (view != null) {
            deviceListRecyclerView = findViewById(R.id.device_list_recycler_view)
            val emptyListTextView: TextView = findViewById(R.id.empty_list_text_view)
            deviceListRecyclerView.layoutManager =
                GridLayoutManager(context, UiUtils().getRecyclerSpanCount(this))

            val adapter = deviceListAdapter
            deviceListRecyclerView.adapter = adapter

            when (DozeRequest().isOnline(this)) {
                true -> {
                    deviceListRecyclerView.visibility = View.VISIBLE
                    emptyListTextView.visibility = View.GONE

                    val deviceListJson = runBlocking {
                        withContext(Dispatchers.IO) {
                            DozeRequest().getFirmwareLatest()
                        }
                    }
                    val responseParamsArray = deviceListJson.toMap()
                    val keys = responseParamsArray.keys

                    for (i in keys) {
                        val jsonObject = deviceListJson.getJSONObject(i)

                        val deviceNameValue = jsonObject.getString("name")
                        val deviceIconValue = when {
                            deviceNameValue.contains(deviceNamesArray[1], true) -> {
                                deviceIconArray[1]
                            }
                            deviceNameValue.contains(deviceNamesArray[2], true) -> {
                                deviceIconArray[2]
                            }
                            else -> {
                                deviceIconArray[0]
                            }
                        }
                        val firmwareVersionValue = jsonObject.getString("fw")
                        val firmwareReleaseDateValue = jsonObject.getString("date")

                        val firmwareUpdated = getString(R.string.firmware_updated)
                        val firmwareChangelogValue = "$firmwareUpdated: $firmwareVersionValue"

                        title = getString(R.string.feed)

                        deviceListAdapter.addDevice(
                            DeviceListData(
                                deviceNameValue,
                                deviceIconValue,
                                firmwareReleaseDateValue,
                                firmwareChangelogValue,
                                i.toInt()
                            )
                        )
                        deviceListAdapter.notifyItemInserted(i.toInt())
                        deviceListIndex[deviceNameValue] = i.toInt()
                    }

                    if (state != null) {
                        deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
                    }
                }
                false -> {
                    deviceListRecyclerView.visibility = View.GONE
                    emptyListTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        state = deviceListRecyclerView.layoutManager?.onSaveInstanceState()
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