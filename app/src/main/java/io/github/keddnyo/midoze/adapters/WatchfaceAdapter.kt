package io.github.keddnyo.midoze.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Watchface

class WatchfaceAdapter : RecyclerView.Adapter<WatchfaceAdapter.WatchfaceListViewHolder>() {
    private var watchfaceArray = ArrayList<Watchface>()
    private lateinit var context: Context
    private var hasCategories: Boolean = true

    class WatchfaceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: CoordinatorLayout =
            itemView.findViewById(R.id.watchfaceLayout)
        val title: TextView =
            itemView.findViewById(R.id.watchfaceTitle)
        val preview: ImageView =
            itemView.findViewById(R.id.watchfacePreview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchfaceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.watchface, parent, false)
        return WatchfaceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchfaceListViewHolder, position: Int) {
        context = holder.layout.context
        val watchface = watchfaceArray[position]

        holder.title.text = watchface.title
        holder.preview.setImageBitmap(watchface.preview)

        if (!hasCategories) {
            holder.layout.layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
            holder.preview.layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
        }

        if ((watchface.preview.height > (watchface.preview.width - (watchface.preview.width / 4))) && (watchface.preview.height < (watchface.preview.width + (watchface.preview.width / 4)))) {
            holder.preview.layoutParams.height = CoordinatorLayout.LayoutParams.WRAP_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return watchfaceArray.size
    }

    fun addWatchfaceList(watchfaceList: ArrayList<Watchface>) {
        watchfaceArray = watchfaceList
    }

    fun setHasCategories(bool: Boolean) {
        hasCategories = bool
    }
}