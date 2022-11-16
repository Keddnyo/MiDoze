package io.github.keddnyo.midoze.remote.models.watchface

import io.github.keddnyo.midoze.local.models.watchface.WatchfaceWearable

data class WatchfaceList(
    val device: WatchfaceWearable,
    val watchfaceList: ArrayList<Watchface>
)