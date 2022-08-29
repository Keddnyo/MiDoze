package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.local.dataModels.WatchfaceDevice

object WatchfaceRepository {
    val watchfaceDeviceStack = arrayListOf(
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(25).name,
            alias = "mijia.watch.band01",
            hasCategories = false
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(59).name,
            alias = "hmpace.bracelet.v5",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(59).name,
            alias = "hmpace.bracelet.v5h",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(212).name,
            alias = "hmpace.motion.v6",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(211).name,
            alias = "hmpace.motion.v6nfc",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(263).name,
            alias = "hmpace.watch.v7",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = DeviceRepository().getDeviceNameByCode(260).name,
            alias = "hmpace.watch.v7nfc",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hqbd3.watch.l67",
            alias = "hqbd3.watch.l67",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "hqbd3.watch.l67in",
            alias = "hqbd3.watch.l67in",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.ds",
            alias = "midr.watch.ds",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k62",
            alias = "midr.watch.k62",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k62a",
            alias = "midr.watch.k62a",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k63",
            alias = "midr.watch.k63",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k63a",
            alias = "midr.watch.k63a",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k65",
            alias = "midr.watch.k65",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k65in",
            alias = "midr.watch.k65in",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.k65w",
            alias = "midr.watch.k65w",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "mijia.watch.m69gl",
            alias = "mijia.watch.m69gl",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "mijia.watch.v1",
            alias = "mijia.watch.v1",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "midr.watch.sports",
            alias = "midr.watch.sports",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "mijia.watch.l61",
            alias = "mijia.watch.l61",
            hasCategories = true
        ),
        WatchfaceDevice(
            title = "Xiaomi Mi Watch",
            alias = "xiaomi.watch.mars",
            hasCategories = true
        )
    )
}