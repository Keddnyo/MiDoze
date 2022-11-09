package io.github.keddnyo.midoze.local.models.firmware

import io.github.keddnyo.midoze.local.repository.WearableRegion

data class Device(
    val deviceSource: Int,
    val productionSource: Int,
    val application: Application,
)