package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.local.dataModels.WatchfaceDevice

object WatchfaceRepository {
    val watchfaceDeviceStack = arrayListOf(
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(25).name,
            deviceAlias = "mijia.watch.band01",
            hasCategories = false
        ),
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(59).name,
            deviceAlias = "hmpace.bracelet.v5",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(59).name,
            deviceAlias = "hmpace.bracelet.v5h",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(212).name,
            deviceAlias = "hmpace.motion.v6",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(211).name,
            deviceAlias = "hmpace.motion.v6nfc",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(263).name,
            deviceAlias = "hmpace.watch.v7",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = DeviceRepository().getDeviceNameByCode(260).name,
            deviceAlias = "hmpace.watch.v7nfc",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "hqbd3.watch.l67",
            deviceAlias = "hqbd3.watch.l67",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "hqbd3.watch.l67in",
            deviceAlias = "hqbd3.watch.l67in",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.ds",
            deviceAlias = "midr.watch.ds",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k62",
            deviceAlias = "midr.watch.k62",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k62a",
            deviceAlias = "midr.watch.k62a",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k63",
            deviceAlias = "midr.watch.k63",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k63a",
            deviceAlias = "midr.watch.k63a",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k65",
            deviceAlias = "midr.watch.k65",
            hasCategories = false
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k65in",
            deviceAlias = "midr.watch.k65in",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.k65w",
            deviceAlias = "midr.watch.k65w",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "mijia.watch.m69gl",
            deviceAlias = "mijia.watch.m69gl",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "mijia.watch.v1",
            deviceAlias = "mijia.watch.v1",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "midr.watch.sports",
            deviceAlias = "midr.watch.sports",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "mijia.watch.l61",
            deviceAlias = "mijia.watch.l61",
            hasCategories = true
        ),
        WatchfaceDevice(
            deviceName = "Xiaomi Mi Watch",
            deviceAlias = "xiaomi.watch.mars",
            hasCategories = true
        )
    )
}