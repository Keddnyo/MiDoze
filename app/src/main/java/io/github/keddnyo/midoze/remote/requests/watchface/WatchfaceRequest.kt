package io.github.keddnyo.midoze.remote.requests.watchface

import io.github.keddnyo.midoze.remote.models.watchface.Watchface
import io.github.keddnyo.midoze.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getWatchface(
    deviceName: String
): ArrayList<Watchface>? {
    val country = LocaleUtils().currentCountry
    val language = LocaleUtils().currentLanguage

    val response = withContext(Dispatchers.IO) {
        StringBuilder()
            .append("https")
            .append("://")
            .append("watch-appstore.iot.mi.com")
            .append("/api/watchface/prize/tabs?")
            .append("model=$deviceName")
            .toURL()
            .openConnection()
            .run {
                setRequestProperty("Watch-Appstore-Common", "_locale=$country&_language=$language")
                inputStream
            }
            .getJsonResponse()
    }

    response.getJsonArrayOrNull("data") ?: return null

    val watchfaceArray: ArrayList<Watchface> = arrayListOf()

    val data = response.getJSONArray("data")

    (0..data.length()).forEach data@ { d ->
        val dataObject = data.getJSONObject(d)

        val tabName = dataObject.getString("tab_name")

        response.getJsonArrayOrNull("list") ?: return@data
        val list = dataObject.getJSONArray("list")

        (0..list.length()).forEach list@ { l ->

            list.getJSONObject(l).run {

                val title = getStringOrNull("display_name") ?: return@list
                val preview = getStringOrNull("icon")?.getImage() ?: return@list
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