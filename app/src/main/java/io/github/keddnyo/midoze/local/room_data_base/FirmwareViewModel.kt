package io.github.keddnyo.midoze.local.room_data_base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import io.github.keddnyo.midoze.remote.FirmwareDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class FirmwareViewModel(
    application: Application
): AndroidViewModel(application) {

    private val dataBase: FirmwareDataBase
    private val repository: FirmwareRepository

    init {
        dataBase = FirmwareDataBase.getInstance(application)
        repository = FirmwareRepository(firmwareDAO = dataBase.firmwareDao())
    }

    fun add(firmware: FirmwareDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(firmware = firmware)
        }
    }

    fun read(id: Int): LiveData<FirmwareDataModel> {
        return repository.read(id = id)
    }

    fun readAll(): Flow<PagingData<FirmwareDataModel>> {
        val localPagingSource = repository.firmwares
        val remotePagingSource = FirmwareDataSource()

        val localPager = Pager(
            PagingConfig(
                pageSize = 10
            )
        ) {
            localPagingSource
        }.flow.cachedIn(viewModelScope)

        val remotePager = Pager(
            PagingConfig(
                pageSize = 10
            )
        ) {
            remotePagingSource
        }.flow.cachedIn(viewModelScope)

        return remotePager

    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete()
        }
    }

}