package io.github.keddnyo.midoze.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.WatchfaceActivity
import io.github.keddnyo.midoze.local.dataModels.WatchfaceCommonStack
import io.github.keddnyo.midoze.utils.BitmapCache

class WatchfaceCommonAdapter : RecyclerView.Adapter<WatchfaceCommonAdapter.DeviceListViewHolder>() {
    private var watchfaceCommonStackArray = ArrayList<WatchfaceCommonStack>()
    private lateinit var context: Context

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.watchfaceCommonStackLayout)
        val title: TextView =
            itemView.findViewById(R.id.watchfaceCommonStackTitle)
        val icon: ImageView =
            itemView.findViewById(R.id.watchfaceCommonStackIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.watchface_common_stack, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        context = holder.layout.context

        val gson = Gson()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val preview = watchfaceCommonStackArray[position].stack[0].stack[0].preview

        holder.title.text = watchfaceCommonStackArray[position].title
        holder.icon.setImageBitmap(BitmapCache().decode(preview))

        holder.layout.setOnClickListener {
            val intent = Intent(context, WatchfaceActivity::class.java)
            prefs.edit().putString("WatchfaceStack", gson.toJson(watchfaceCommonStackArray[position].stack).toString()).apply()
            intent.putExtra("name", watchfaceCommonStackArray[position].title)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return watchfaceCommonStackArray.size
    }

    fun addWatchfaceList(array: ArrayList<WatchfaceCommonStack>) {
        watchfaceCommonStackArray = array
        notifyItemRangeInserted(0, array.size)
    }
}