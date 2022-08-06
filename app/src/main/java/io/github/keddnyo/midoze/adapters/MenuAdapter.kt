package io.github.keddnyo.midoze.adapters

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
import io.github.keddnyo.midoze.activities.main.AppsActivity
import io.github.keddnyo.midoze.activities.main.DeviceStackActivity
import io.github.keddnyo.midoze.activities.request.RequestActivity
import io.github.keddnyo.midoze.local.dataModels.MainMenu
import io.github.keddnyo.midoze.remote.Routes
import io.github.keddnyo.midoze.utils.AndroidVersion
import io.github.keddnyo.midoze.utils.PackageUtils

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.DeviceListViewHolder>() {
    private var mainMenuArray = ArrayList<MainMenu>()
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
            .inflate(R.layout.menu_item, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        context = holder.layout.context

        holder.title.text = mainMenuArray[position].title
        holder.icon.setImageResource(mainMenuArray[position].icon)

        holder.layout.setOnClickListener {
            when (mainMenuArray[position].tag) {
                "Apps" -> {
                    startActivity(Intent(context, AppsActivity::class.java))
                }
                "Firmwares" -> {
                    startActivity(Intent(context, DeviceStackActivity::class.java))
                }
                "Request" -> {
                    startActivity(Intent(context, RequestActivity::class.java))
                }
                "Downloads" -> {
                    startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
                }
                "Uninstall" -> {
                    PackageUtils(context).removePackage(context.packageName)
                }
                "About" -> {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(Routes.GITHUB_APP_REPOSITORY))
                    )
                }
            }
        }

        if (!AndroidVersion().isLollipopOrHigher) {
            holder.icon.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mainMenuArray.size
    }

    fun addItems(mainMenuArrayList: ArrayList<MainMenu>) {
        mainMenuArray = mainMenuArrayList
        notifyItemRangeInserted(0, mainMenuArrayList.size)
    }

    private fun startActivity(intent: Intent) = with(context) {
        startActivity(intent)
    }
}