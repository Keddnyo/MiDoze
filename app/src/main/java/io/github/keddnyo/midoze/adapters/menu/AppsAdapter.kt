package io.github.keddnyo.midoze.adapters.menu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.Menu
import io.github.keddnyo.midoze.utils.PackageUtils

class AppsAdapter : RecyclerView.Adapter<AppsAdapter.DeviceListViewHolder>() {
    private var appsArray = ArrayList<Menu>()
    private lateinit var context: Context

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.applicationLayout)
        val title: TextView =
            itemView.findViewById(R.id.applicationTitle)
        val icon: ImageView =
            itemView.findViewById(R.id.applicationIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.application, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        context = holder.layout.context

        holder.title.text = appsArray[position].title
        holder.icon.setImageResource(appsArray[position].icon)

        holder.layout.setOnClickListener {
            appsArray.forEach { application ->
                if (appsArray[position].tag == application.tag) {
                    if (isPackageInstalled(application.tag)) {
                        context.packageManager.getLaunchIntentForPackage(application.tag)?.let {
                            context.startActivity(it)
                        }
                    } else {
                        when (appsArray[position].tag) {
                            "nodomain.freeyourgadget.gadgetbridge" -> {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://gadgetbridge.org/")
                                    )
                                )
                            }
                            "nodomain.nopackage.huafetcher" -> {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://codeberg.org/vanous/huafetcher/")
                                    )
                                )
                            }
                            else -> {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=${application.tag}")
                                        )
                                    )
                                } catch (e: ActivityNotFoundException) {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://play.google.com/store/apps/details?id=${application.tag}")
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        holder.layout.setOnLongClickListener {
            appsArray.forEach { application ->
                if (appsArray[position].tag == application.tag) {
                    PackageUtils(context, application.tag).removePackage()
                }
            }

            true
        }
    }

    override fun getItemCount(): Int {
        return appsArray.size
    }

    fun addItems(array: ArrayList<Menu>) {
        appsArray = array
        notifyItemRangeInserted(0, array.size)
    }

    private fun isPackageInstalled(packageName: String): Boolean = with(context) {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun startActivity(intent: Intent) = with(context) {
        startActivity(intent)
    }
}