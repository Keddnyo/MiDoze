package io.github.keddnyo.midoze.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.FirmwareActivity
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import kotlin.collections.ArrayList

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.DeviceListViewHolder>() {
    private var deviceArray = ArrayList<FirmwareData>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView =
            itemView.findViewById(R.id.stackNameTextView)
        val image: ImageView =
            itemView.findViewById(R.id.stackImageView)
        val fwVersion: TextView =
            itemView.findViewById(R.id.stackCountTextView)

        val deviceLayout: MaterialCardView = itemView.findViewById(R.id.downloadLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.name.text = deviceArray[position].device.name
        holder.image.setImageResource(deviceArray[position].device.image)
        holder.fwVersion.text = deviceArray[position].firmwareVersion

        fun openFirmwareActivity(
            context: Context,
            custom: Boolean
        ) {
            val intent = if (custom) {
                Intent(context, RequestActivity::class.java)
            } else {
                Intent(context, FirmwareActivity::class.java)
            }

            intent.putExtra("deviceName", deviceArray[position].device.name)
            intent.putExtra("deviceIcon", deviceArray[position].device.image)
            intent.putExtra("firmwareData", deviceArray[position].firmware.toString())

            intent.putExtra("productionSource", deviceArray[position].wearable.productionSource)
            intent.putExtra("deviceSource", deviceArray[position].wearable.deviceSource)
            intent.putExtra("appName", deviceArray[position].wearable.application.name)
            intent.putExtra("appVersion", deviceArray[position].wearable.application.version)

            intent.putExtra("country", deviceArray[position].wearable.region.country)
            intent.putExtra("lang", deviceArray[position].wearable.region.language)

            context.startActivity(intent)
        }

        holder.deviceLayout.setOnClickListener {
            openFirmwareActivity(
                holder.deviceLayout.context,
                false
            )
        }

        holder.deviceLayout.setOnLongClickListener {
            openFirmwareActivity(
                holder.deviceLayout.context,
                true
            )
            true
        }
    }

    override fun getItemCount(): Int {
        return deviceArray.size
    }

    fun addDevice(firmwareDataArray: ArrayList<FirmwareData>) {
        deviceArray = firmwareDataArray
    }
}