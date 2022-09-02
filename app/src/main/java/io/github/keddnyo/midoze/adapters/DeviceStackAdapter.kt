package io.github.keddnyo.midoze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.fragments.DeviceContainer
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack

class DeviceStackAdapter(private var stackArray: ArrayList<FirmwareDataStack> = arrayListOf()) : RecyclerView.Adapter<DeviceStackAdapter.DeviceListViewHolder>() {

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: MaterialCardView =
            itemView.findViewById(R.id.downloadLayout)
        val image: ImageView =
            itemView.findViewById(R.id.stackImageView)
        val name: TextView? =
            itemView.findViewById(R.id.stackNameTextView)
        val count: TextView? =
            itemView.findViewById(R.id.stackCountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        return DeviceListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.device_stack, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.image.setImageResource(stackArray[position].deviceStack[0].device.image)
        holder.name?.text = stackArray[position].name
        stackArray[position].deviceStack.size.toString().let { deviceCount ->
            holder.count?.text = holder.container.context.getString(R.string.items, deviceCount)
        }

        holder.container.setOnClickListener {
            DeviceContainer().show((holder.container.context as AppCompatActivity), stackArray, position)
        }
    }

    override fun getItemCount(): Int {
        return stackArray.size
    }
}