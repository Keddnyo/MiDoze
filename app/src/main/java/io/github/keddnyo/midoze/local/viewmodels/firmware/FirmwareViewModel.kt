package io.github.keddnyo.midoze.local.viewmodels.firmware

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import io.github.keddnyo.midoze.remote.paging.FirmwarePagingSource

class FirmwareViewModel : ViewModel() {

    val firmwareList = Pager(
        PagingConfig(
            pageSize = 1,
        )
    ) {
        FirmwarePagingSource()
    }.flow.cachedIn(viewModelScope)

}