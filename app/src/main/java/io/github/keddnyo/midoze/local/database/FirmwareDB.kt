package io.github.keddnyo.midoze.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel

@Database(entities = [FirmwareDataModel::class], version = 1, exportSchema = false)
abstract class FirmwareDB : RoomDatabase() {
    abstract fun firmwareDao(): FirmwareDao

    companion object {

        private var INSTANCE: FirmwareDB? = null

        fun getInstance(context: Context): FirmwareDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FirmwareDB::class.java,
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