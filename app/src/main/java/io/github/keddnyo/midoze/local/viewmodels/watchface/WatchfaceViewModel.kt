package io.github.keddnyo.midoze.local.viewmodels.watchface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.keddnyo.midoze.remote.paging.WatchfacePagingSource

class WatchfaceViewModel : ViewModel() {

    val watchfaceList = Pager(
        PagingConfig(
            pageSize = 1,
        )
    ) {
        WatchfacePagingSource()
    }.flow.cachedIn(viewModelScope)

}