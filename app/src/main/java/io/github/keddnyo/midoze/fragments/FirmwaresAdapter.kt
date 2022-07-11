package io.github.keddnyo.midoze.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.FirmwareActivity
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.devices.DeviceData
import java.util.*

class FirmwaresAdapter : RecyclerView.Adapter<FirmwaresAdapter.DeviceListViewHolder>(), Filterable {
    private var firmwaresDataArray = ArrayList<DeviceData>()
    private var firmwaresDataArrayFull = ArrayList<DeviceData>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTextView: TextView =
            itemView.findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView =
            itemView.findViewById(R.id.deviceIconImageView)
        val firmwareReleaseDateTextView: TextView =
            itemView.findViewById(R.id.firmwareReleaseDateTextView)
        val firmwareVersionTextView: TextView =
            itemView.findViewById(R.id.firmwareVersionTextView)

        val feedCustomRequest: ImageView = itemView.findViewById(R.id.feedCustomRequest)
        val downloadLayout: MaterialCardView = itemView.findViewById(R.id.downloadLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_feed_firmware, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val prefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(holder.deviceNameTextView.context)
        val editor = prefs.edit()

        holder.deviceNameTextView.text = firmwaresDataArray[position].name
        holder.deviceIconImageView.setImageResource(firmwaresDataArray[position].icon)
        holder.firmwareReleaseDateTextView.text = firmwaresDataArray[position].buildTime
        holder.firmwareVersionTextView.text = firmwaresDataArray[position].firmwareVersion

        val deviceIndex = firmwaresDataArray[position].deviceSource

        fun openFirmwareActivity(
            context: Context,
            custom: Boolean
        ) {
            val intent = if (custom) {
                Intent(context, RequestActivity::class.java)
            } else {
                Intent(context, FirmwareActivity::class.java)
            }

            intent.putExtra("deviceName", firmwaresDataArray[position].name)
            intent.putExtra("firmwareData", firmwaresDataArray[position].firmware.toString())

            intent.putExtra("productionSource", firmwaresDataArray[position].productionSource)
            intent.putExtra("deviceSource", firmwaresDataArray[position].deviceSource)
            intent.putExtra("appName", firmwaresDataArray[position].appName)
            intent.putExtra("appVersion", firmwaresDataArray[position].appVersion)

            context.startActivity(intent)
        }

        holder.feedCustomRequest.setOnClickListener {
            openFirmwareActivity(
                holder.downloadLayout.context,
                true
            )
        }

        holder.downloadLayout.setOnClickListener {
            openFirmwareActivity(
                holder.downloadLayout.context,
                false
            )
        }
    }

    override fun getItemCount(): Int {
        return firmwaresDataArray.size
    }

    fun addDevice(deviceDataArray: ArrayList<DeviceData>) {
        firmwaresDataArray = deviceDataArray
        firmwaresDataArrayFull = firmwaresDataArray
    }

    override fun getFilter(): Filter {
        return deviceFilter
    }

    private val deviceFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<DeviceData> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(firmwaresDataArrayFull)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in firmwaresDataArrayFull) {
                    if (item.name.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            clear()
            firmwaresDataArray.addAll(results.values as Collection<DeviceData>)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        firmwaresDataArray.clear()
    }
}