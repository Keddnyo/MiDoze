package io.github.keddnyo.midoze.adapters

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
import io.github.keddnyo.midoze.fragments.DeviceFragment
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack

class DeviceStackAdapter : RecyclerView.Adapter<DeviceStackAdapter.DeviceListViewHolder>() {
    private var stackArray = ArrayList<FirmwareDataStack>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView? =
            itemView.findViewById(R.id.stackNameTextView)
        val image: ImageView =
            itemView.findViewById(R.id.stackImageView)
        val count: TextView? =
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

        holder.name?.text = stackArray[position].name
        holder.image.setImageResource(stackArray[position].deviceStack[0].device.image)
        holder.count?.text = holder.stackLayout.context.getString(R.string.items, deviceCount)

        holder.stackLayout.setOnClickListener {
            val context = holder.stackLayout.context as AppCompatActivity
            val gson = Gson()
//            val intent = Intent(holder.stackLayout.context, DeviceActivity::class.java)
//            intent.putExtra("NAME", stackArray[position].name)
//            intent.putExtra("DEVICE_ARRAY", gson.toJson(stackArray[position].deviceStack))
//            holder.stackLayout.context.startActivity(intent)

            val deviceFragment = DeviceFragment()
            val args = Bundle()
            args.putString(
                "deviceArray",
                gson.toJson(stackArray[position].deviceStack)
            )

            deviceFragment.arguments = args
            val fm = context.supportFragmentManager
            if (!fm.isDestroyed) {
                fm.beginTransaction()
                    .replace(R.id.deviceFrame, deviceFragment)
                    .commit()
            }

            context.title = stackArray[position].name
        }
    }

    override fun getItemCount(): Int {
        return stackArray.size
    }

    fun addDevice(firmwareDataArray: ArrayList<FirmwareDataStack>) {
        val count = itemCount
        stackArray.clear()
        notifyItemRangeRemoved(0, count)

        stackArray = firmwareDataArray
        notifyItemRangeInserted(0, stackArray.size)
    }
}