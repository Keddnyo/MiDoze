package io.github.keddnyo.midoze.remote.requests.watchface

import io.github.keddnyo.midoze.remote.models.watchface.Watchface
import io.github.keddnyo.midoze.utils.LocaleUtils
import io.github.keddnyo.midoze.utils.getJsonResponse
import io.github.keddnyo.midoze.utils.getOrNull
import io.github.keddnyo.midoze.utils.toURL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun getWatchface(
    deviceName: String
): ArrayList<Watchface> {
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

    val watchfaceArray: ArrayList<Watchface> = arrayListOf()

    val data = response.getJSONArray("data")

    for (d in 0 until data.length()) {
        val dataObject = data.getJSONObject(d)

        val tabName = dataObject.getString("tab_name")
        val list = dataObject.getJSONArray("list")

        for (l in 0 until list.length()) {

            list.getJSONObject(l).run {

                val title = getOrNull("display_name")
                val preview = getOrNull("icon")
                val introduction = getOrNull("introduction")
                val watchfaceLink = getOrNull("config_file")

                if (title != null && preview != null && watchfaceLink != null) {
                    watchfaceArray.add(
                        Watchface(
                            tabName = tabName,
                            title = title,
                            preview = preview,
                            introduction = introduction,
                            watchfaceLink = watchfaceLink
                        )
                    )
                }

            }
        }
    }

    return watchfaceArray
}