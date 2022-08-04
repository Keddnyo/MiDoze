package io.github.keddnyo.midoze.adapters

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.Toolbox

class ToolboxAdapter : RecyclerView.Adapter<ToolboxAdapter.DeviceListViewHolder>() {
    private var toolboxArray = ArrayList<Toolbox>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.toolboxItemLayout)
        val title: TextView =
            itemView.findViewById(R.id.toolboxItemTitle)
        val icon: ImageView =
            itemView.findViewById(R.id.toolboxItemIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.toolbox_item, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val context = holder.layout.context

        holder.title.text = toolboxArray[position].title
        holder.icon.setImageResource(toolboxArray[position].icon)

        holder.layout.setOnClickListener {
            when (toolboxArray[position].tag) {
                "Request" -> {
                    context.startActivity(Intent(context, RequestActivity::class.java))
                }
                "Downloads" -> {
                    context.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
                }
                "Uninstall" -> {
                    context.startActivity(
                        Intent(Intent.ACTION_DELETE).apply {
                            data = Uri.parse("package:${context.packageName}")
                        }
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return toolboxArray.size
    }

    fun addItems(toolboxArrayList: ArrayList<Toolbox>) {
        toolboxArray = toolboxArrayList
        notifyItemRangeInserted(0, toolboxArrayList.size)
    }
}