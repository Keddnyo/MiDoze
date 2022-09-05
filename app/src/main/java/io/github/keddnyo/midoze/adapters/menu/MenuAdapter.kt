package io.github.keddnyo.midoze.adapters.menu

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.watchface.WatchfaceStackActivity
import io.github.keddnyo.midoze.activities.firmware.DeviceStackActivity
import io.github.keddnyo.midoze.activities.menu.ApplicationsActivity
import io.github.keddnyo.midoze.activities.firmware.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.Menu
import io.github.keddnyo.midoze.remote.Routes
import io.github.keddnyo.midoze.utils.AndroidVersion
import io.github.keddnyo.midoze.utils.PackageUtils

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.DeviceListViewHolder>() {
    private var menuArray = ArrayList<Menu>()
    private lateinit var context: Context

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.toolboxItemLayout)
        val title: TextView =
            itemView.findViewById(R.id.mainMenuItemTitle)
        val icon: ImageView =
            itemView.findViewById(R.id.mainMenuItemIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        context = holder.layout.context

        holder.title.text = menuArray[position].title
        holder.icon.setImageResource(menuArray[position].icon)

        holder.layout.setOnClickListener {
            when (menuArray[position].tag) {
                "Apps" -> {
                    startActivity(Intent(context, ApplicationsActivity::class.java))
                }
                "Firmwares" -> {
                    startActivity(Intent(context, DeviceStackActivity::class.java))
                }
                "Request" -> {
                    startActivity(Intent(context, RequestActivity::class.java))
                }
                "Watchface" -> {
                    startActivity(Intent(context, WatchfaceStackActivity::class.java))
                }
                "Downloads" -> {
                    startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
                }
                "Uninstall" -> {
                    PackageUtils(context, context.packageName).removePackage()
                }
                "About" -> {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(Routes.GITHUB_APP_REPOSITORY))
                    )
                }
            }
        }

        if (!AndroidVersion().isLollipopOrAbove) {
            holder.icon.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return menuArray.size
    }

    fun addItems(menuArrayList: ArrayList<Menu>) {
        menuArray = menuArrayList
        notifyItemRangeInserted(0, menuArrayList.size)
    }

    private fun startActivity(intent: Intent) = with(context) {
        startActivity(intent)
    }
}