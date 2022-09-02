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

class FirmwarePreviewAdapter(private val deviceList: ArrayList<FirmwareData>, private val downloadContent: String) : RecyclerView.Adapter<FirmwarePreviewAdapter.PreviewViewHolder>() {
    inner class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_preview_item, parent, false)
        return PreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.itemView.context.let { context ->
            deviceList[position].let { device ->
                fun openResponseActivity() {
                    val intent = Intent(context, ResponseActivity::class.java)
                    intent.putExtra("json", device.toString())
                    context.startActivity(intent)
                }

                holder.itemView.findViewById<ImageView>(R.id.firmwarePreview).let { preview ->
                    preview.setImageResource(device.device.image)
                    preview.setOnLongClickListener {
                        openResponseActivity()
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