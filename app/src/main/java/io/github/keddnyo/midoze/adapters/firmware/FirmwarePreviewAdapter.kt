package io.github.keddnyo.midoze.adapters.firmware

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.firmware.request.ResponseActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData

class FirmwarePreviewAdapter(private val deviceList: ArrayList<FirmwareData.FirmwareData>) : RecyclerView.Adapter<FirmwarePreviewAdapter.PreviewViewHolder>() {
    inner class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview, parent, false)
        return PreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.itemView.context.let {
            deviceList[position].let { device ->
                holder.itemView.findViewById<ImageView>(R.id.menuPreview).run {
                    setImageResource(device.device.preview)
                    setOnLongClickListener {
                        Intent(context, ResponseActivity::class.java).let { intent ->
                            intent.putExtra("json", deviceList[position].firmwareData.toString())
                            context.startActivity(intent)
                        }
                        true
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }
}