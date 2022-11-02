package io.github.keddnyo.midoze.local.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "firmwares")
data class FirmwareDataModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firmwareVersion: String? = null,
    val firmwareUrl: String? = null,
    val resourceVersion: String? = null,
    val resourceUrl: String? = null,
    val baseResourceVersion: String? = null,
    val baseResourceUrl: String? = null,
    val fontVersion: String? = null,
    val fontUrl: String? = null,
    val gpsVersion: String? = null,
    val gpsUrl: String? = null,
    val changeLog: String? = null,
    val buildTime: String? = null,
)