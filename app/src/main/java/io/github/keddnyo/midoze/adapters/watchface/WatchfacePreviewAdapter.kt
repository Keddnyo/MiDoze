package io.github.keddnyo.midoze.adapters.watchface

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.WatchfaceData
import io.github.keddnyo.midoze.utils.BitmapCache

class WatchfacePreviewAdapter(private val watchfaceList: ArrayList<WatchfaceData.Watchface>) : RecyclerView.Adapter<WatchfacePreviewAdapter.PreviewViewHolder>() {
    inner class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.preview, parent, false)
        return PreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        val currentImage = BitmapCache(holder.itemView.context).decode(watchfaceList[position].alias, watchfaceList[position].title)
        holder.itemView.findViewById<ImageView>(R.id.menuPreview).setImageBitmap(currentImage)
    }

    override fun getItemCount(): Int {
        return watchfaceList.size
    }
}