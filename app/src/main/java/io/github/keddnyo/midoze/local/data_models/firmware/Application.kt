package io.github.keddnyo.midoze.local.data_models.firmware

import io.github.keddnyo.midoze.local.repository.WearableApplication

data class Application (
    val instance: WearableApplication,
    val version: String,
)