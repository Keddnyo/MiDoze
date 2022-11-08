package io.github.keddnyo.midoze.local.models.firmware

import io.github.keddnyo.midoze.local.repository.WearableApplication

data class Application (
    val instance: WearableApplication,
    val appVersion: String,
)