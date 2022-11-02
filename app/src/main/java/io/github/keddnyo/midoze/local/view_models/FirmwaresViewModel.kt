package io.github.keddnyo.midoze.local.view_models

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import io.github.keddnyo.midoze.local.database.FirmwareDB
import io.github.keddnyo.midoze.local.database.FirmwareDao
import io.github.keddnyo.midoze.local.database.FirmwareRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirmwaresViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FirmwareRepository(FirmwareDB.getInstance(application).firmwareDao())

    init {
        viewModelScope.launch {
            getFirmwares()
        }
    }

    var firmwares by mutableStateOf(
        emptyList<FirmwareDataModel>()
    )

    var firmware by mutableStateOf(
        FirmwareDataModel(
            id = 0,
            firmwareVersion = null,
            firmwareUrl = null,
            resourceVersion = null,
            resourceUrl = null,
            baseResourceVersion = null,
            baseResourceUrl = null,
            fontVersion = null,
            fontUrl = null,
            gpsVersion = null,
            gpsUrl = null,
            changeLog = null,
            buildTime = null,
        )
    )

    fun getFirmwares() {
        viewModelScope.launch {
            repository.getFirmwaresFromRoom().collect { response ->
                firmwares = response
            }
        }
    }

    fun getFirmware(id: Int) {
        viewModelScope.launch {
            repository.getFirmwareFromRoom(id = id).collect { response ->
                firmware = response
            }
        }
    }

    fun addFirmware(firmware: FirmwareDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFirmwareToRoom(firmware = firmware)
        }
    }

    fun updateFirmware(firmware: FirmwareDataModel) {
        viewModelScope.launch {
            repository.updateFirmwareInRoom(firmware = firmware)
        }
    }

    fun deleteFirmware(firmware: FirmwareDataModel) {
        viewModelScope.launch {
            repository.deleteFirmwareFromRoom(firmware = firmware)
        }
    }
}