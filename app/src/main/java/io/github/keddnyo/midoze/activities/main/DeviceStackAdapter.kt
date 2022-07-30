package io.github.keddnyo.midoze.activities.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import io.ktor.client.engine.*
import kotlin.collections.ArrayList

class DeviceStackAdapter : RecyclerView.Adapter<DeviceStackAdapter.DeviceListViewHolder>() {
    private var firmwaresDataArray = ArrayList<FirmwareDataStack>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTextView: TextView =
            itemView.findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView =
            itemView.findViewById(R.id.deviceIconImageView)
        val firmwareVersionTextView: TextView =
            itemView.findViewById(R.id.firmwareVersionTextView)

        val downloadLayout: MaterialCardView = itemView.findViewById(R.id.downloadLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_stack, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val deviceCount = firmwaresDataArray[position].deviceStack.size.toString()

        holder.deviceNameTextView.text = firmwaresDataArray[position].name
        holder.deviceIconImageView.setImageResource(firmwaresDataArray[position].deviceStack[firmwaresDataArray[position].deviceStack.lastIndex].device.image)
        holder.firmwareVersionTextView.text = holder.downloadLayout.context.getString(R.string.items, deviceCount)

        holder.downloadLayout.setOnClickListener {
            val gson = Gson()
            val intent = Intent(holder.downloadLayout.context, DeviceListActivity::class.java)
            intent.putExtra("NAME", firmwaresDataArray[position].name)
            intent.putExtra("DEVICE_ARRAY", gson.toJson(firmwaresDataArray[position].deviceStack))
            holder.downloadLayout.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return firmwaresDataArray.size
    }

    fun addDevice(firmwareDataArray: ArrayList<FirmwareDataStack>) {
        firmwaresDataArray = firmwareDataArray
    }

    fun clear() {
        firmwaresDataArray.clear()
    }
}