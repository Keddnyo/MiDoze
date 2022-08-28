package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.local.dataModels.WatchfaceDevice

object WatchfaceRepository {
    val watchfaceDeviceStack = arrayListOf(
        WatchfaceDevice(
            title = "hqbd3.watch.l67",
            alias = "hqbd3.watch.l67",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hmpace.bracelet.v5h",
            alias = "hmpace.bracelet.v5h",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(25, 256).name,
            alias = "mijia.watch.band01",
            hasCategories = false
        ),
        WatchfaceDevice(
            title = "mijia.watch.v1",
            alias = "mijia.watch.v1",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k65",
            alias = "midr.watch.k65",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.ds",
            alias = "midr.watch.ds",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k63",
            alias = "midr.watch.k63",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k62",
            alias = "midr.watch.k62",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "mijia.watch.l61",
            alias = "mijia.watch.l61",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "xiaomi.watch.mars",
            alias = "xiaomi.watch.mars",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hmpace.motion.v6",
            alias = "hmpace.motion.v6",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hmpace.motion.v6nfc",
            alias = "hmpace.motion.v6nfc",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.sports",
            alias = "midr.watch.sports",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hmpace.bracelet.v5",
            alias = "hmpace.bracelet.v5",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hmpace.watch.v7nfc",
            alias = "hmpace.watch.v7nfc",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hmpace.watch.v7",
            alias = "hmpace.watch.v7",
            hasCategories = true
        )
    )
}