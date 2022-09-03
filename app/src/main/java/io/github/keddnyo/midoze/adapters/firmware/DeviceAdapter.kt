package io.github.keddnyo.midoze.adapters.firmware

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.FirmwarePreviewActivity
import io.github.keddnyo.midoze.activities.firmware.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.Firmware
import kotlin.collections.ArrayList

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.DeviceListViewHolder>() {
    private var deviceArray = ArrayList<Firmware.FirmwareData>()

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
        holder.image.setImageResource(deviceArray[position].device.preview)
        holder.fwVersion.text = deviceArray[position].firmwareData.getString("firmwareVersion")

        val gson = Gson()

        fun openFirmwareActivity(
            context: Context,
            custom: Boolean
        ) {
            val intent = if (custom) {
                Intent(context, RequestActivity::class.java)
            } else {
                Intent(context, FirmwarePreviewActivity::class.java)
            }

            intent.putExtra("position", position)
            intent.putExtra("firmwareArray", gson.toJson(deviceArray).toString())

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

    fun addDevice(firmwareArray: ArrayList<Firmware.FirmwareData>) {
        deviceArray = firmwareArray
    }
}