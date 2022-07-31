package io.github.keddnyo.midoze.activities.main.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.DeviceActivity
import io.github.keddnyo.midoze.fragments.DeviceFragment
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack

class DeviceStackAdapter : RecyclerView.Adapter<DeviceStackAdapter.DeviceListViewHolder>() {
    private var stackArray = ArrayList<FirmwareDataStack>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView =
            itemView.findViewById(R.id.stackNameTextView)
        val image: ImageView =
            itemView.findViewById(R.id.stackImageView)
        val count: TextView =
            itemView.findViewById(R.id.stackCountTextView)

        val stackLayout: MaterialCardView = itemView.findViewById(R.id.downloadLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_stack, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val deviceCount = stackArray[position].deviceStack.size.toString()
        val context = holder.stackLayout.context

        holder.name.text = stackArray[position].name
        holder.image.setImageResource(stackArray[position].deviceStack[stackArray[position].deviceStack.lastIndex].device.image)
        holder.count.text = context.getString(R.string.items, deviceCount)

        holder.stackLayout.setOnClickListener {
            val gson = Gson()
            val stackName = stackArray[position].name

            if (context.resources.getBoolean(R.bool.isTablet)) {
                val deviceFragment = DeviceFragment()
                val args = Bundle()
                args.putString("DEVICE_ARRAY", gson.toJson(stackArray[position].deviceStack))
                deviceFragment.arguments = args

                (context as AppCompatActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.deviceFragmentTablet, deviceFragment)
                    .commit()

                context.title = stackName

            } else {
                val intent = Intent(context, DeviceActivity::class.java)
                intent.putExtra("NAME", stackName)
                intent.putExtra("DEVICE_ARRAY", gson.toJson(stackArray[position].deviceStack))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return stackArray.size
    }

    fun addDevice(firmwareDataArray: ArrayList<FirmwareDataStack>) {
        stackArray = firmwareDataArray
    }

    fun clear() {
        stackArray.clear()
    }
}