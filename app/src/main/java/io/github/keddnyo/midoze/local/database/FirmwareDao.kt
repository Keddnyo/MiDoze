package io.github.keddnyo.midoze.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FirmwareDaoOld {

    @Query("SELECT * FROM firmwares ORDER BY id ASC")
    fun getFirmwares(): Flow<List<FirmwareDataModel>>

    @Query("SELECT * FROM firmwares WHERE id = :id")
    fun getFirmware(id: Int): Flow<FirmwareDataModel>

    @Insert(onConflict = IGNORE)
    fun addFirmware(firmware: FirmwareDataModel)

    @Update
    fun updateFirmware(firmware: FirmwareDataModel)

    @Delete
    fun deleteFirmware(firmware: FirmwareDataModel)

}
