package io.github.keddnyo.midoze.remote

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.AppVersion
import io.github.keddnyo.midoze.utils.AsyncTask
import io.github.keddnyo.midoze.utils.Display
import org.json.JSONObject

class Updates(val context: Context) : AsyncTask() {
    override fun execute() {
        super.execute()

        var releaseData = JSONObject("{}")
        if (Requests().isHostAvailable(Routes.GITHUB_RELEASE_DATA_PAGE)) {
            releaseData = Requests().getAppReleaseData()
        }

        mainHandler.post {
            if (releaseData.has("tag_name") && releaseData.getJSONArray("assets")
                    .toString() != "[]"
            ) {
                val latestVersion = releaseData.getString("tag_name")
                val releaseChangelog = releaseData.getString("body")
                val latestVersionLink =
                    releaseData.getJSONArray("assets").getJSONObject(0)
                        .getString("browser_download_url")

                if (AppVersion(context).name < latestVersion) {
                    val builder = AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.update_title, latestVersion))
                        .setMessage(releaseChangelog)
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                    builder.setPositiveButton(R.string.update_button) { _: DialogInterface?, _: Int ->
                        Requests().getFirmwareFile(context,
                            latestVersionLink,
                            context.getString(R.string.app_name))
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