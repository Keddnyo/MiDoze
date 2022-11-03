package io.github.keddnyo.midoze.local.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import io.github.keddnyo.midoze.remote.FirmwareDataSource

class FirmwareViewModel : ViewModel() {

    val firmwareList = Pager(
        PagingConfig(
            pageSize = 1,
            prefetchDistance = 2,
        )
    ) {
        FirmwareDataSource()
    }.flow.cachedIn(viewModelScope)

}