package io.github.keddnyo.midoze.adapters.watchface

import android.content.Context
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
import io.github.keddnyo.midoze.activities.watchface.WatchfacePreviewActivity
import io.github.keddnyo.midoze.local.dataModels.WatchfaceData
import io.github.keddnyo.midoze.utils.BitmapCache

class WatchfaceAdapter(private val watchfaceDataStack: ArrayList<WatchfaceData.WatchfaceArray> = ArrayList()) : RecyclerView.Adapter<WatchfaceAdapter.DeviceListViewHolder>() {
    private lateinit var context: Context

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.menuLayout)
        val title: TextView =
            itemView.findViewById(R.id.menuTitle)
        val preview: ImageView =
            itemView.findViewById(R.id.menuPreview)
        val count: TextView? =
            itemView.findViewById(R.id.menuSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        context = holder.layout.context

        holder.title.text = watchfaceDataStack[position].name
        try {
            BitmapCache(context).decode(watchfaceDataStack[position].watchface[0].alias, watchfaceDataStack[position].watchface[0].title).let {
                holder.preview.setImageBitmap(it)
            }
            
            watchfaceDataStack[position].watchface.size.toString().let { count ->
                holder.count?.text = holder.layout.context.getString(R.string.items, count)
            }

            holder.layout.setOnClickListener {
                Intent(context, WatchfacePreviewActivity::class.java).let { intent ->
                    intent.putExtra("watchfaceArray", Gson().toJson(watchfaceDataStack[position]).toString())
                    context.startActivity(intent)
                }
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return watchfaceDataStack.size
    }
}