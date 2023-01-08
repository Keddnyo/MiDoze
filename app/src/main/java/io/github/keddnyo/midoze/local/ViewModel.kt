package io.github.keddnyo.midoze.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import io.github.keddnyo.midoze.remote.requests.Paging

class ViewModel : ViewModel() {

    val firmwareList = Pager(
        PagingConfig(1)
    ) {
        Paging()
    }.flow.cachedIn(viewModelScope)

}