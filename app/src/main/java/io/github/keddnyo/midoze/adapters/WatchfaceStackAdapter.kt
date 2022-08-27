package io.github.keddnyo.midoze.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.WatchfaceStack
import io.github.keddnyo.midoze.utils.Display

class WatchfaceStackAdapter : RecyclerView.Adapter<WatchfaceStackAdapter.WatchfaceStackViewHolder>() {
    private var watchfaceStackArray = ArrayList<WatchfaceStack>()
    private lateinit var context: Context

    class WatchfaceStackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.watchfaceStackLayout)
        val title: TextView =
            itemView.findViewById(R.id.watchfaceStackTitle)
        val stack: RecyclerView =
            itemView.findViewById(R.id.watchfaceStackList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchfaceStackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.watchface_stack, parent, false)
        return WatchfaceStackViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchfaceStackViewHolder, position: Int) {
        context = holder.layout.context

        holder.title.text = watchfaceStackArray[position].title
        holder.stack.let { RecyclerView ->
            RecyclerView.layoutManager = LinearLayoutManager(holder.layout.context, LinearLayoutManager.HORIZONTAL, false)

            WatchfaceAdapter().let { adapter ->
                RecyclerView.adapter = adapter

                adapter.addWatchfaceList(
                    watchfaceStackArray[position].stack
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return watchfaceStackArray.size
    }

    fun addWatchfaceList(watchfaceList: ArrayList<WatchfaceStack>) {
        watchfaceStackArray = watchfaceList
    }
}