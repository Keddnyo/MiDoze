package io.github.keddnyo.midoze.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.ResponseActivity
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.utils.BitmapCache

class FirmwarePreviewAdapter(private val deviceList: ArrayList<FirmwareData>) : RecyclerView.Adapter<FirmwarePreviewAdapter.PreviewViewHolder>() {
    inner class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_preview_item, parent, false)
        return PreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.itemView.context.let {
            deviceList[position].let { device ->
                holder.itemView.findViewById<ImageView>(R.id.firmwarePreview).setImageResource(device.device.image)
            }
        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }
}