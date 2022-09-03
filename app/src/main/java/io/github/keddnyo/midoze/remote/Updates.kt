package io.github.keddnyo.midoze.remote

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.OnlineStatus
import io.github.keddnyo.midoze.utils.StringUtils.getPackageVersion
import org.json.JSONObject

class Updates(val context: Context) : AsyncTask() {
    override fun execute() {
        super.execute()

        val releaseData =
            OnlineStatus(context).run {
                if (isOnline && isHostAvailable(Routes.GITHUB_RELEASE_DATA_PAGE)) {
                    Requests().getAppReleaseData()
                } else {
                    JSONObject("{}")
                }
            }

        mainHandler.post {
            if (
                android.os.Build.VERSION.SDK_INT >= 21 &&
                releaseData.has("tag_name") &&
                releaseData.getJSONArray("assets").toString() != "[]"
            ) {
                val latestVersion = releaseData.getString("tag_name")
                val releaseChangelog = releaseData.getString("body")
                val latestVersionLink =
                    releaseData.getJSONArray("assets").getJSONObject(0)
                        .getString("browser_download_url")

                if (context.getPackageVersion() < latestVersion) {
                    val builder = AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.update_title, latestVersion))
                        .setMessage(releaseChangelog)
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                    builder.setPositiveButton(R.string.update_button) { _: DialogInterface?, _: Int ->
                        Requests().getFirmwareFile(
                            context,
                            latestVersionLink,
                            context.getString(R.string.app_name),
                            context.getString(R.string.app_name)
                        )
                        DialogInterface.BUTTON_POSITIVE
                    }
                    builder.setNegativeButton(android.R.string.cancel) { _: DialogInterface?, _: Int ->
                        DialogInterface.BUTTON_NEGATIVE
                    }
                    builder.show()
                }
            }
        }
    }
}