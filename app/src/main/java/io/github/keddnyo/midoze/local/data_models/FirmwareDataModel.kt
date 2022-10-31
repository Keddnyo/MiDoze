package io.github.keddnyo.midoze.local.data_models

data class FirmwareDataModel (
    val firmwareVersion: String? = null,
    val firmwareUrl: String? = null,
    val resourceVersion: String? = null,
    val resourceUrl: String? = null,
    val changeLog: String? = null,
    val buildTime: String? = null,
)