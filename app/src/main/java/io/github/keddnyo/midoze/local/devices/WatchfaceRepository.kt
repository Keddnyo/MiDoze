package io.github.keddnyo.midoze.local.devices

import io.github.keddnyo.midoze.local.dataModels.Watchface

object WatchfaceRepository {
    val DeviceStacks = arrayListOf(
        Watchface.Device(
            name = DeviceRepository().getDeviceNameByCode(25).name,
            preview = DeviceRepository().getDeviceNameByCode(25).preview,
            alias = "mijia.watch.band01"
        ),
        Watchface.Device(
            name = DeviceRepository().getDeviceNameByCode(59).name,
            preview = DeviceRepository().getDeviceNameByCode(59).preview,
            alias = "hmpace.bracelet.v5"
        ),
//        Watchface.Device(
//            name = DeviceRepository().getDeviceNameByCode(59).name,
//            preview = DeviceRepository().getDeviceNameByCode(59).preview,
//            alias = "hmpace.bracelet.v5h"
//        ),
        Watchface.Device(
            name = DeviceRepository().getDeviceNameByCode(212).name,
            preview = DeviceRepository().getDeviceNameByCode(212).preview,
            alias = "hmpace.motion.v6"
        ),
//        Watchface.Device(
//            name = DeviceRepository().getDeviceNameByCode(211).name,
//            preview = DeviceRepository().getDeviceNameByCode(211).preview,
//            alias = "hmpace.motion.v6nfc"
//        ),
        Watchface.Device(
            name = DeviceRepository().getDeviceNameByCode(263).name,
            preview = DeviceRepository().getDeviceNameByCode(263).preview,
            alias = "hmpace.watch.v7"
        ),
//        Watchface.Device(
//            name = DeviceRepository().getDeviceNameByCode(260).name,
//            preview = DeviceRepository().getDeviceNameByCode(260).preview,
//            alias = "hmpace.watch.v7nfc"
//        ),
        Watchface.Device(
            name = "hqbd3.watch.l67",
            preview = null,
            alias = "hqbd3.watch.l67"
        ),
        Watchface.Device(
            name = "hqbd3.watch.l67in",
            preview = null,
            alias = "hqbd3.watch.l67in"
        ),
        Watchface.Device(
            name = "midr.watch.ds",
            preview = null,
            alias = "midr.watch.ds"
        ),
        Watchface.Device(
            name = "midr.watch.k62",
            preview = null,
            alias = "midr.watch.k62"
        ),
        Watchface.Device(
            name = "midr.watch.k62a",
            preview = null,
            alias = "midr.watch.k62a"
        ),
        Watchface.Device(
            name = "midr.watch.k63",
            preview = null,
            alias = "midr.watch.k63"
        ),
        Watchface.Device(
            name = "midr.watch.k63a",
            preview = null,
            alias = "midr.watch.k63a"
        ),
        Watchface.Device(
            name = "midr.watch.k65",
            preview = null,
            alias = "midr.watch.k65"
        ),
        Watchface.Device(
            name = "midr.watch.k65in",
            preview = null,
            alias = "midr.watch.k65in"
        ),
        Watchface.Device(
            name = "midr.watch.k65w",
            preview = null,
            alias = "midr.watch.k65w"
        ),
        Watchface.Device(
            name = "mijia.watch.m69gl",
            preview = null,
            alias = "mijia.watch.m69gl"
        ),
        Watchface.Device(
            name = "mijia.watch.v1",
            preview = null,
            alias = "mijia.watch.v1"
        ),
        Watchface.Device(
            name = "midr.watch.sports",
            preview = null,
            alias = "midr.watch.sports"
        ),
        Watchface.Device(
            name = "mijia.watch.l61",
            preview = null,
            alias = "mijia.watch.l61"
        ),
        Watchface.Device(
            name = "Xiaomi Mi Watch",
            preview = null,
            alias = "xiaomi.watch.mars"
        )
    )
}