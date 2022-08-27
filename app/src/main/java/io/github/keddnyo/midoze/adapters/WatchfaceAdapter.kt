package io.github.keddnyo.midoze.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Watchface

class WatchfaceAdapter : RecyclerView.Adapter<WatchfaceAdapter.WatchfaceListViewHolder>() {
    private var watchfaceArray = ArrayList<Watchface>()
    private lateinit var context: Context

    class WatchfaceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.watchfaceLayout)
        val title: TextView =
            itemView.findViewById(R.id.watchfaceTitle)
        val icon: ImageView =
            itemView.findViewById(R.id.watchfacePreview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchfaceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.watchface, parent, false)
        return WatchfaceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchfaceListViewHolder, position: Int) {
        context = holder.layout.context

        holder.title.text = watchfaceArray[position].title
        holder.icon.setImageBitmap(watchfaceArray[position].preview)

    }

    override fun getItemCount(): Int {
        return watchfaceArray.size
    }

    fun addWatchfaceList(watchfaceList: ArrayList<Watchface>) {
        watchfaceArray = watchfaceList
    }
}