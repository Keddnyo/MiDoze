package io.github.keddnyo.midoze.local.room_data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel

@Database(entities = [FirmwareDataModel::class], version = 1, exportSchema = false)
abstract class FirmwareDataBase : RoomDatabase() {
    abstract fun firmwareDao(): FirmwareDAO

    companion object {

        private var INSTANCE: FirmwareDataBase? = null

        fun getInstance(context: Context): FirmwareDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FirmwareDataBase::class.java,
                        "firmwares"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

    }
}