package io.github.keddnyo.midoze.local.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import io.github.keddnyo.midoze.remote.FirmwareDataSource

class FirmwareViewModel : ViewModel() {

    val firmwareList = Pager(
        PagingConfig(
            pageSize = 1,
        )
    ) {
        FirmwareDataSource()
    }.flow.cachedIn(viewModelScope)

}