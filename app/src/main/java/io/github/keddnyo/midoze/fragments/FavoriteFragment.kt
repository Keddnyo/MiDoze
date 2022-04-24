package io.github.keddnyo.midoze.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.ExtrasRequestActivity
import io.github.keddnyo.midoze.utils.DozeRequest
import io.github.keddnyo.midoze.utils.UiUtils
import io.github.keddnyo.midoze.utils.deviceList.DeviceListAdapter
import io.github.keddnyo.midoze.utils.deviceList.DeviceListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class FavoriteFragment : Fragment() {

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
        setHasOptionsMenu(true)

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

        val fab: FloatingActionButton = findViewById(R.id.feed_floating_button)

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

                        val prefs: SharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(this)

                        title = getString(R.string.favorites_title)

                        if (prefs.getInt("favoriteCount", 0) == 0) {
                            deviceListRecyclerView.visibility = View.GONE
                            emptyListTextView.visibility = View.VISIBLE
                            emptyListTextView.text = getString(R.string.add_favorites)
                        } else {
                            deviceListRecyclerView.visibility = View.VISIBLE
                            emptyListTextView.visibility = View.GONE
                        }

                        if (prefs.getBoolean(i, false)) {
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
                    }

                    if (state != null) {
                        deviceListRecyclerView.layoutManager?.onRestoreInstanceState(state)
                    }
                }
                false -> {
                    deviceListRecyclerView.visibility = View.GONE
                    emptyListTextView.visibility = View.VISIBLE
                    emptyListTextView.text = getString(R.string.check_connectivity)
                }
            }
        }

        fab.setOnClickListener {
            startActivity(Intent(requireContext(), ExtrasRequestActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()

        state = deviceListRecyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorite, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear_favorites -> {
                showClearDialog(requireContext())
            }
        }
        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())

        if (prefs.getInt("favoriteCount",0) < 1) {
            menu.findItem(R.id.action_clear_favorites)?.isVisible = false
        }

        return super.onPrepareOptionsMenu(menu)
    }

    private fun showClearDialog(context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            .setTitle(R.string.favorites_clear)
            .setMessage(R.string.favorites_clear_summary)
            .setIcon(R.drawable.ic_info)
            .setCancelable(false)

        builder.setNegativeButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
            clearFavorites()
            builder.show().dismiss()
        }
        builder.setPositiveButton(android.R.string.cancel) { _: DialogInterface?, _: Int ->
            builder.show().dismiss()
        }
        builder.show()
    }
    
    private fun clearFavorites() {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = prefs.edit()
        
        for (i in 0..1000) {
            editor.putBoolean(i.toString(), false)
            editor.apply()
        }

        editor.putInt("favoriteCount", 0)
        editor.apply()

        reloadFragment()
    }

    private fun reloadFragment() {
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, FeedFragment())?.commitNow()
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, this)?.commitNow()
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