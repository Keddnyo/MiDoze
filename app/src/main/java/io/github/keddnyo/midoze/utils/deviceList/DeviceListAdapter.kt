package io.github.keddnyo.midoze.utils.deviceList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import java.util.*

class DeviceListAdapter : RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder>(), Filterable {
    private val deviceListDataArray = ArrayList<DeviceListData>()
    private var deviceListDataArrayFull = ArrayList<DeviceListData>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTextView: TextView =
            itemView.findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView =
            itemView.findViewById(R.id.deviceIconImageView)
        val firmwareVersionTextView: TextView =
            itemView.findViewById(R.id.firmwareVersionTextView)
        val firmwareReleaseDateTextView: TextView =
            itemView.findViewById(R.id.firmwareReleaseDateTextView)
        val firmwareChangelogTextView: TextView =
            itemView.findViewById(R.id.firmwareChangelogTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_device, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.deviceNameTextView.text = deviceListDataArray[position].deviceName
        holder.deviceIconImageView.setImageResource(deviceListDataArray[position].deviceIcon)
        holder.firmwareVersionTextView.text = deviceListDataArray[position].firmwareVersion
        holder.firmwareReleaseDateTextView.text = deviceListDataArray[position].firmwareReleaseDate
        holder.firmwareChangelogTextView.text = deviceListDataArray[position].firmwareChangelog
    }

    override fun getItemCount(): Int {
        return deviceListDataArray.size
    }

    fun addDevice(deviceListData: DeviceListData) {
        deviceListDataArray.add(deviceListData)
        deviceListDataArrayFull = ArrayList(deviceListDataArray)
    }

    override fun getFilter(): Filter {
        return deviceFilter
    }

    private val deviceFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<DeviceListData> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(deviceListDataArrayFull)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in deviceListDataArrayFull) {
                    if (item.deviceName.lowercase(Locale.getDefault()).contains(filterPattern)) {
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
            deviceListDataArray.clear()
            deviceListDataArray.addAll(results.values as Collection<DeviceListData>)
            notifyDataSetChanged()
        }
    }

    fun getDeviceName(position: Int): String {
        return deviceListDataArray[position].deviceName
    }
}