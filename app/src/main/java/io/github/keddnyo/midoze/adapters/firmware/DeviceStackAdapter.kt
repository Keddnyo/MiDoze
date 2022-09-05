package io.github.keddnyo.midoze.adapters.firmware

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.FirmwarePreviewActivity
import io.github.keddnyo.midoze.local.dataModels.Firmware

class DeviceStackAdapter(private var stackArray: ArrayList<Firmware.FirmwareDataArray> = arrayListOf()) : RecyclerView.Adapter<DeviceStackAdapter.DeviceListViewHolder>() {

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.firmwareLayout)
        val preview: ImageView =
            itemView.findViewById(R.id.firmwarePreview)
        val title: TextView? =
            itemView.findViewById(R.id.firmwareTitle)
        val count: TextView? =
            itemView.findViewById(R.id.firmwareCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        return DeviceListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.firmware, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.preview.setImageResource(stackArray[position].firmwareData[0].device.preview)
        holder.title?.text = stackArray[position].name
        stackArray[position].firmwareData.size.toString().let { count ->
            holder.count?.text = holder.layout.context.getString(R.string.items, count)
        }

        holder.layout.run {
            setOnClickListener {
                Intent(context, FirmwarePreviewActivity::class.java).apply {
                    putExtra("firmwareArray",
                        Gson().toJson(stackArray[position].firmwareData))
                    context.startActivity(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return stackArray.size
    }
}