package io.github.keddnyo.midoze.local.viewmodels.watchface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.keddnyo.midoze.remote.models.watchface.Watchface
import io.github.keddnyo.midoze.remote.requests.watchface.getWatchface
import kotlinx.coroutines.launch

class WatchfaceViewModel : ViewModel() {

    private val _watchfaceList: ArrayList<Watchface> = arrayListOf()

    val watchfaceList
        get() = _watchfaceList

    init {
        viewModelScope.launch {
            getWatchface("hmpace.watch.v7")?.let {
                _watchfaceList.addAll(
                    it
                )
            }
        }
    }

}