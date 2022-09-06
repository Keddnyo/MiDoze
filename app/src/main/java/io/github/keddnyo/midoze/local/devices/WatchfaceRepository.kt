package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.local.dataModels.WatchfaceData

object WatchfaceRepository {
    val DeviceStacks = arrayListOf(
        WatchfaceData.Device(
            name = DeviceRepository().getDeviceNameByCode(25).name,
            preview = DeviceRepository().getDeviceNameByCode(25).preview,
            alias = "mijia.watch.band01"
        ),
        WatchfaceData.Device(
            name = DeviceRepository().getDeviceNameByCode(59).name,
            preview = DeviceRepository().getDeviceNameByCode(59).preview,
            alias = "hmpace.bracelet.v5"
        ),
//        WatchfaceData.Device(
//            name = DeviceRepository().getDeviceNameByCode(59).name,
//            preview = DeviceRepository().getDeviceNameByCode(59).preview,
//            alias = "hmpace.bracelet.v5h"
//        ),
        WatchfaceData.Device(
            name = DeviceRepository().getDeviceNameByCode(212).name,
            preview = DeviceRepository().getDeviceNameByCode(212).preview,
            alias = "hmpace.motion.v6"
        ),
//        WatchfaceData.Device(
//            name = DeviceRepository().getDeviceNameByCode(211).name,
//            preview = DeviceRepository().getDeviceNameByCode(211).preview,
//            alias = "hmpace.motion.v6nfc"
//        ),
        WatchfaceData.Device(
            name = DeviceRepository().getDeviceNameByCode(263).name,
            preview = DeviceRepository().getDeviceNameByCode(263).preview,
            alias = "hmpace.watch.v7"
        ),
//        WatchfaceData.Device(
//            name = DeviceRepository().getDeviceNameByCode(260).name,
//            preview = DeviceRepository().getDeviceNameByCode(260).preview,
//            alias = "hmpace.watch.v7nfc"
//        ),
        WatchfaceData.Device(
            name = "Xiaomi Smart Band 7 Pro",
            preview = null,
            alias = "hqbd3.watch.l67"
        ),
        WatchfaceData.Device(
            name = "Xiaomi Smart Band 7 Pro In",
            preview = null,
            alias = "hqbd3.watch.l67in"
        ),
        WatchfaceData.Device(
            name = "midr.watch.ds",
            preview = null,
            alias = "midr.watch.ds"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k62",
            preview = null,
            alias = "midr.watch.k62"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k62a",
            preview = null,
            alias = "midr.watch.k62a"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k63",
            preview = null,
            alias = "midr.watch.k63"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k63a",
            preview = null,
            alias = "midr.watch.k63a"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k65",
            preview = null,
            alias = "midr.watch.k65"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k65in",
            preview = null,
            alias = "midr.watch.k65in"
        ),
        WatchfaceData.Device(
            name = "midr.watch.k65w",
            preview = null,
            alias = "midr.watch.k65w"
        ),
        WatchfaceData.Device(
            name = "mijia.watch.m69gl",
            preview = null,
            alias = "mijia.watch.m69gl"
        ),
        WatchfaceData.Device(
            name = "mijia.watch.v1",
            preview = null,
            alias = "mijia.watch.v1"
        ),
        WatchfaceData.Device(
            name = "midr.watch.sports",
            preview = null,
            alias = "midr.watch.sports"
        ),
        WatchfaceData.Device(
            name = "mijia.watch.l61",
            preview = null,
            alias = "mijia.watch.l61"
        ),
        WatchfaceData.Device(
            name = "Xiaomi Mi Watch",
            preview = null,
            alias = "xiaomi.watch.mars"
        )
    )
}