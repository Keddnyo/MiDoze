package io.github.keddnyo.midoze.local.room_data_base

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel

@Dao
interface FirmwareDAO {

    @Insert(onConflict = IGNORE)
    suspend fun add(firmware: FirmwareDataModel)

    @Query("SELECT * FROM firmwares WHERE id = :id")
    fun read(id: Int): LiveData<FirmwareDataModel>

    @Query("SELECT * FROM firmwares ORDER BY id DESC")
    fun readAll(): PagingSource<Int, FirmwareDataModel>

    @Query("DELETE FROM firmwares")
    suspend fun deleteAll()

}