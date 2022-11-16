package io.github.keddnyo.midoze.remote.requests.entities.watchface

import io.github.keddnyo.midoze.local.repositories.watchface.watchfaceWearables
import io.github.keddnyo.midoze.remote.models.watchface.WatchfaceList

suspend fun getWatchfaceList(
    i: Int
) : ArrayList<WatchfaceList> {
    val array = arrayListOf<WatchfaceList>()

    array.run {
        val deviceName = watchfaceWearables[i]

        getWatchface(
            deviceCodeName = deviceName.deviceCodeName
        ).let {
            add(
                WatchfaceList(
                    device = deviceName,
                    watchfaceList = it
                )
            )
        }
    }

    return array
}