package io.github.keddnyo.midoze.utils.deviceList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R

class DeviceListAdapter : RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder>() {
    private val deviceListDataArray = ArrayList<DeviceListData>()

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
    }
}