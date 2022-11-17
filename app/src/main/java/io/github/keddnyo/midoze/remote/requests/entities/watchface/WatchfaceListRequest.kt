package io.github.keddnyo.midoze.remote.requests.entities.watchface

import io.github.keddnyo.midoze.local.repositories.watchface.watchfaceWearables
import io.github.keddnyo.midoze.remote.models.watchface.WatchfaceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

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
                    watchfaceList = it
                )
            )
        }
    }

    return array
}