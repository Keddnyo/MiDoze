package io.github.keddnyo.midoze.remote.requests.entities.watchface

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.github.keddnyo.midoze.remote.models.watchface.Watchface
import io.github.keddnyo.midoze.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun getWatchface(
    deviceCodeName: String
): ArrayList<Watchface> {
    val country = LocaleUtils().currentCountry
    val language = LocaleUtils().currentLanguage

    val response = withContext(Dispatchers.IO) {
        StringBuilder()
            .append("https")
            .append("://")
            .append("watch-appstore.iot.mi.com")
            .append("/api/watchface/prize/tabs?")
            .append("model=$deviceCodeName")
            .toURL()
            .openConnection()
            .run {
                setRequestProperty("Watch-Appstore-Common", "_locale=$country&_language=$language")
                connectTimeout = 5000
                readTimeout = 5000
                inputStream
            }
            .getJsonResponse()
    }

    val watchfaceArray: ArrayList<Watchface> = arrayListOf()

    if (!response.has("data")) return watchfaceArray

    val data = response.getJSONArray("data")

    (0 until data.length()).forEach data@{ d ->

        val dataObject = data.getJSONObject(d)

        val tabName = dataObject.getString("tab_name")

        if (!dataObject.has("list")) return@data

        val list = dataObject.getJSONArray("list")

        (0 until list.length()).forEach list@{ l ->

            list.getJSONObject(l).run {

                val title = getStringOrNull("display_name") ?: return@list
                val preview = getStringOrNull("icon") ?: return@list
                val watchfaceLink = getStringOrNull("config_file") ?: return@list

                watchfaceArray.add(
                    Watchface(
                        tabName = tabName,
                        title = title,
                        preview = preview,
                        watchfaceLink = watchfaceLink
                    )
                )

            }
        }
    }

    return watchfaceArray
}