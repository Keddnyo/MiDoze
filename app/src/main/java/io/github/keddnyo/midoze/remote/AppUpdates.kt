package io.github.keddnyo.midoze.remote

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AlertDialog
import io.github.keddnyo.midoze.BuildConfig
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.utils.Display
import org.json.JSONObject

@SuppressLint("StaticFieldLeak")
class AppUpdates(private val prefs: SharedPreferences, val context: Context) :
    AsyncTask<Void?, Void?, Void>() {

    var releaseData: JSONObject = JSONObject("{}")

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg p0: Void?): Void? = with(context) {
        if (prefs.getBoolean("settings_app_check_updates",
                true) && DozeRequest().getHostReachable() != null
        ) {
            releaseData = DozeRequest().getAppReleaseData()
        }
        return null
    }

    @SuppressLint("InlinedApi")
    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Void?) = with(context) {
        super.onPostExecute(result)

        if (releaseData.has("tag_name") && releaseData.getJSONArray("assets")
                .toString() != "[]"
        ) {
            val latestVersion = releaseData.getString("tag_name")
            val releaseChangelog = releaseData.getString("body")
            val latestVersionLink =
                releaseData.getJSONArray("assets").getJSONObject(0)
                    .getString("browser_download_url")

            if (BuildConfig.VERSION_NAME < latestVersion) {
                fun showUpdateDialog() {
                    val builder = AlertDialog.Builder(context)
                        .setTitle("${getString(R.string.update_dialog_title)} $latestVersion")
                        .setMessage(releaseChangelog)
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                    builder.setPositiveButton(R.string.update_dialog_button) { _: DialogInterface?, _: Int ->
                        DozeRequest().getFirmwareFile(context,
                            latestVersionLink,
                            getString(R.string.app_name))
                        Display().showToast(context,
                            getString(R.string.downloading_toast))
                        DialogInterface.BUTTON_POSITIVE
                    }
                    builder.setNegativeButton(android.R.string.cancel) { _: DialogInterface?, _: Int ->
                        DialogInterface.BUTTON_NEGATIVE
                    }
                    builder.show()
                }
                showUpdateDialog()
            }
        }
    }
}