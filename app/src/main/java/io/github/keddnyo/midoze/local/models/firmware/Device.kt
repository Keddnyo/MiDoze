package io.github.keddnyo.midoze.local.models.firmware

import io.github.keddnyo.midoze.local.repository.Region

data class Device(
    val deviceName: String,
    val devicePreview: Int,
    val deviceSource: Int,
    val productionSource: Int,
    val application: Application,
    val region: Region,
)