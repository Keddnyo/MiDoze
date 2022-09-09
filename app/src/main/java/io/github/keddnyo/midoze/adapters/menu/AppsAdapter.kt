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
import io.github.keddnyo.midoze.local.dataModels.ApplicationData
import io.github.keddnyo.midoze.utils.PackageUtils

class AppsAdapter(private val appsArray: ArrayList<ApplicationData.Application> = ArrayList()) : RecyclerView.Adapter<AppsAdapter.DeviceListViewHolder>() {

    private lateinit var context: Context

    class DeviceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: MaterialCardView =
            itemView.findViewById(R.id.menuLayout)
        val preview: ImageView =
            itemView.findViewById(R.id.menuPreview)
        val title: TextView =
            itemView.findViewById(R.id.menuTitle)
        val subtitle: TextView =
            itemView.findViewById(R.id.menuSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu, parent, false)
        return DeviceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        context = holder.layout.context

        holder.preview.setImageResource(appsArray[position].icon)
        holder.title.text = appsArray[position].title

        appsArray.forEach { application ->
            if (appsArray[position].tag == application.tag) {
                holder.subtitle.text =
                    PackageUtils(context, application.tag).getPackageVersionName()
                        ?: context.getString(R.string.not_installed)
            }
        }

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

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun startActivity(intent: Intent) = with(context) {
        startActivity(intent)
    }
}