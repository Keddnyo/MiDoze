package io.github.keddnyo.midoze.fragments

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

class FirmwaresAdapter : RecyclerView.Adapter<FirmwaresAdapter.DeviceListViewHolder>(), Filterable {
    private var firmwaresDataArray = ArrayList<FirmwareData>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTextView: TextView =
            itemView.findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView =
            itemView.findViewById(R.id.deviceIconImageView)
        val firmwareReleaseDateTextView: TextView =
            itemView.findViewById(R.id.firmwareReleaseDateTextView)
        val firmwareVersionTextView: TextView =
            itemView.findViewById(R.id.firmwareVersionTextView)

        val downloadLayout: MaterialCardView = itemView.findViewById(R.id.downloadLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_feed_firmware, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.deviceNameTextView.text = firmwaresDataArray[position].wearable.name
        holder.deviceIconImageView.setImageResource(firmwaresDataArray[position].wearable.image)
        holder.firmwareReleaseDateTextView.text = firmwaresDataArray[position].buildTime
        holder.firmwareVersionTextView.text = firmwaresDataArray[position].firmwareVersion

        fun openFirmwareActivity(
            context: Context,
            custom: Boolean
        ) {
            val intent = if (custom) {
                Intent(context, RequestActivity::class.java)
            } else {
                Intent(context, FirmwareActivity::class.java)
            }

            intent.putExtra("deviceName", firmwaresDataArray[position].wearable.name)
            intent.putExtra("deviceIcon", firmwaresDataArray[position].wearable.image)
            intent.putExtra("firmwareData", firmwaresDataArray[position].firmware.toString())

            intent.putExtra("productionSource", firmwaresDataArray[position].productionSource)
            intent.putExtra("deviceSource", firmwaresDataArray[position].deviceSource)
            intent.putExtra("appName", firmwaresDataArray[position].application.name)
            intent.putExtra("appVersion", firmwaresDataArray[position].application.version)

            context.startActivity(intent)
        }

        holder.downloadLayout.setOnClickListener {
            openFirmwareActivity(
                holder.downloadLayout.context,
                false
            )
        }

        holder.downloadLayout.setOnLongClickListener {
            openFirmwareActivity(
                holder.downloadLayout.context,
                true
            )
            true
        }
    }

    override fun getItemCount(): Int {
        return firmwaresDataArray.size
    }

    fun getItems(): ArrayList<FirmwareData> {
        return firmwaresDataArray
    }

    fun addDevice(firmwareDataArray: ArrayList<FirmwareData>) {
        firmwaresDataArray = firmwareDataArray
    }

    fun clear() {
        firmwaresDataArray.clear()
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}