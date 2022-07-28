package io.github.keddnyo.midoze.local.dataModels

data class FirmwareDataStack(
    val name: String,
    val image: Int,
    val deviceStack: ArrayList<FirmwareData>
)