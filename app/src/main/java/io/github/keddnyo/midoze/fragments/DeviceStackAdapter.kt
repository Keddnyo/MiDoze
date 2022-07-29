package io.github.keddnyo.midoze.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Device
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack
import kotlin.collections.ArrayList

class DeviceStackAdapter : RecyclerView.Adapter<DeviceStackAdapter.DeviceListViewHolder>() {
    private var firmwaresDataArray = ArrayList<FirmwareDataStack>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTextView: TextView =
            itemView.findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView =
            itemView.findViewById(R.id.deviceIconImageView)
        val deviceIconImageViewDecorate: ImageView =
            itemView.findViewById(R.id.deviceIconImageViewDecorate)
        val firmwareVersionTextView: TextView =
            itemView.findViewById(R.id.firmwareVersionTextView)

        val downloadLayout: MaterialCardView = itemView.findViewById(R.id.downloadLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_stack, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.deviceNameTextView.text = firmwaresDataArray[position].name
        holder.deviceIconImageView.setImageResource(firmwaresDataArray[position].deviceStack[firmwaresDataArray[position].deviceStack.lastIndex].device.image)
        holder.deviceIconImageViewDecorate.setImageResource(firmwaresDataArray[position].deviceStack[firmwaresDataArray[position].deviceStack.lastIndex].device.image)
        holder.firmwareVersionTextView.text = firmwaresDataArray[position].deviceStack.size.toString()

        holder.downloadLayout.setOnClickListener {
            val gson = Gson()

            val deviceListFragment = DeviceListFragment()
            val args = Bundle()
            args.putString("DEVICE_ARRAY", gson.toJson(firmwaresDataArray[position].deviceStack))
            deviceListFragment.arguments = args

            (holder.downloadLayout.context as AppCompatActivity).supportFragmentManager
                .beginTransaction()
                .add(R.id.deviceListFrame, deviceListFragment)
                .addToBackStack("deviceListFrame")
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return firmwaresDataArray.size
    }

    fun addDevice(firmwareDataArray: ArrayList<FirmwareDataStack>) {
        firmwaresDataArray = firmwareDataArray
    }

    fun clear() {
        firmwaresDataArray.clear()
    }
}