package io.github.keddnyo.midoze.utils.deviceList

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.ExtrasRequestActivity
import io.github.keddnyo.midoze.activities.FirmwareActivity
import io.github.keddnyo.midoze.utils.DozeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class DeviceListAdapter : RecyclerView.Adapter<DeviceListAdapter.DeviceListViewHolder>(), Filterable {
    private val deviceListDataArray = ArrayList<DeviceListData>()
    private var deviceListDataArrayFull = ArrayList<DeviceListData>()

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceNameTextView: TextView =
            itemView.findViewById(R.id.deviceNameTextView)
        val deviceIconImageView: ImageView =
            itemView.findViewById(R.id.deviceIconImageView)
        val firmwareReleaseDateTextView: TextView =
            itemView.findViewById(R.id.firmwareReleaseDateTextView)
        val firmwareChangelogTextView: TextView =
            itemView.findViewById(R.id.firmwareChangelogTextView)

        val likeIcon: ImageView = itemView.findViewById(R.id.likeIcon)

        val likeLayout: LinearLayout = itemView.findViewById(R.id.likeLayout)
        val shareLayout: LinearLayout = itemView.findViewById(R.id.shareLayout)
        val downloadLayout: LinearLayout = itemView.findViewById(R.id.downloadLayout)
        val customLayout: LinearLayout = itemView.findViewById(R.id.customLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_device, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.deviceNameTextView.context)
        val editor = prefs.edit()

        holder.deviceNameTextView.text = deviceListDataArray[position].deviceName
        holder.deviceIconImageView.setImageResource(deviceListDataArray[position].deviceIcon)
        holder.firmwareReleaseDateTextView.text = deviceListDataArray[position].firmwareReleaseDate
        holder.firmwareChangelogTextView.text = deviceListDataArray[position].firmwareChangelog

        val deviceIndex = deviceListDataArray[position].deviceIndex.toString()

        if (prefs.getBoolean(deviceIndex, false)) {
            holder.likeIcon.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.likeIcon.setImageResource(R.drawable.ic_favorite_border)
        }

        holder.downloadLayout.setOnClickListener {
            when (DozeRequest().isOnline(holder.downloadLayout.context)) {
                true -> {
                    openFirmwareActivity(deviceListDataArray[position].deviceIndex, holder.downloadLayout.context, false)
                }
                else -> {
                    // TODO: something
                }
            }
        }

        holder.likeLayout.setOnClickListener {
            if (prefs.getBoolean(deviceIndex, false)) {
                holder.likeIcon.setImageResource(R.drawable.ic_favorite_border)
                editor.putBoolean(deviceIndex, false)
                editor.apply()
            } else {
                holder.likeIcon.setImageResource(R.drawable.ic_favorite)
                editor.putBoolean(deviceIndex, true)
                editor.apply()
            }
        }

        holder.shareLayout.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://schakal.ru/fw/firmwares_list.htm?device=$deviceIndex")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, holder.deviceNameTextView.text)
            holder.shareLayout.context.startActivity(shareIntent)
        }

        holder.customLayout.setOnClickListener {
            when (DozeRequest().isOnline(holder.customLayout.context)) {
                true -> {
                    openFirmwareActivity(deviceListDataArray[position].deviceIndex, holder.customLayout.context, true)
                }
                else -> {
                    // TODO: something
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return deviceListDataArray.size
    }

    fun addDevice(deviceListData: DeviceListData) {
        deviceListDataArray.add(deviceListData)
        deviceListDataArrayFull = ArrayList(deviceListDataArray)
    }

    override fun getFilter(): Filter {
        return deviceFilter
    }

    private val deviceFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<DeviceListData> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(deviceListDataArrayFull)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in deviceListDataArrayFull) {
                    if (item.deviceName.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            clear()
            deviceListDataArray.addAll(results.values as Collection<DeviceListData>)
            notifyDataSetChanged()
        }
    }

    private fun openFirmwareActivity(deviceIndex: Int, context: Context, custom: Boolean) {
        runBlocking {
            withContext(Dispatchers.IO) {
                val jsonObject = JSONObject(DozeRequest().getApplicationValues())

                val deviceNameValue =
                    jsonObject.getJSONObject(deviceIndex.toString()).getString("name")
                val productionSourceValue =
                    jsonObject.getJSONObject(deviceIndex.toString()).getString("productionSource")
                val appNameValue =
                    jsonObject.getJSONObject(deviceIndex.toString()).getString("appname")
                val appVersionValue =
                    jsonObject.getJSONObject(deviceIndex.toString()).getString("appVersion")

                val intent = if (custom) {
                    Intent(context, ExtrasRequestActivity::class.java)
                } else {
                    Intent(context, FirmwareActivity::class.java)
                }

                intent.putExtra("deviceName", deviceNameValue)
                intent.putExtra("productionSource", productionSourceValue)
                intent.putExtra("deviceSource", deviceIndex)
                intent.putExtra("appname", appNameValue)
                intent.putExtra("appVersion", appVersionValue)

                context.startActivity(intent)
            }
        }
    }

    fun clear() {
        deviceListDataArray.clear()
    }
}