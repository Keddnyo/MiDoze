package io.github.keddnyo.midoze.local.database

import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import kotlinx.coroutines.flow.Flow

class FirmwareRepository(private val firmwareDao: FirmwareDao) {
    fun getFirmwaresFromRoom(): Flow<List<FirmwareDataModel>> =
        firmwareDao.getFirmwares()

    fun getFirmwareFromRoom(id: Int): Flow<FirmwareDataModel> =
        firmwareDao.getFirmware(id = id)

    fun addFirmwareToRoom(firmware: FirmwareDataModel) =
        firmwareDao.addFirmware(firmware = firmware)

    fun updateFirmwareInRoom(firmware: FirmwareDataModel) =
        firmwareDao.updateFirmware(firmware = firmware)

    fun deleteFirmwareFromRoom(firmware: FirmwareDataModel) =
        firmwareDao.deleteFirmware(firmware = firmware)
}