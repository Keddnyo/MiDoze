package io.github.keddnyo.midoze.local.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.keddnyo.midoze.remote.FirmwareDataSource

class MyViewModel : ViewModel() {

    val firmwarePager = Pager(
        PagingConfig(pageSize = 1)
    ) {
        FirmwareDataSource()
    }.flow.cachedIn(viewModelScope)

}