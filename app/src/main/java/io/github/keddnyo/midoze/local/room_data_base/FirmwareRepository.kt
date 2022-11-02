package io.github.keddnyo.midoze.local.room_data_base

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel

class FirmwareRepository(
    private val firmwareDAO: FirmwareDAO
) {
    val firmwares: PagingSource<Int, FirmwareDataModel> = firmwareDAO.readAll()

    suspend fun add(firmware: FirmwareDataModel) {
        firmwareDAO.add(firmware = firmware)
    }

    fun read(id: Int): LiveData<FirmwareDataModel> {
        return firmwareDAO.read(id)
    }

    suspend fun delete() {
        firmwareDAO.deleteAll()
    }
}