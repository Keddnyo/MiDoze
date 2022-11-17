package io.github.keddnyo.midoze.remote.requests.entities.watchface

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.github.keddnyo.midoze.local.repositories.watchface.watchfaceWearables
import io.github.keddnyo.midoze.remote.models.watchface.WatchfaceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

suspend fun getWatchfaceList(
    i: Int
) : ArrayList<WatchfaceList> {
    val array = arrayListOf<WatchfaceList>()

    array.run {
        val device = watchfaceWearables[i]

        getWatchface(
            deviceCodeName = device.deviceCodeName
        ).let {
            add(
                WatchfaceList(
                    device = device,
                    preview = it[0].preview.getImage(),
                    watchfaceList = it
                )
            )
        }
    }

    return array
}

suspend fun String.getImage() = withContext(Dispatchers.IO) {
    val url = URL(this@getImage)
    val byteArray = url.openConnection().inputStream.readBytes()
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return@withContext bitmap.asImageBitmap()
}