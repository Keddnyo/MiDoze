package io.github.keddnyo.midoze.local.viewmodels.watchface

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.keddnyo.midoze.remote.models.watchface.Watchface
import io.github.keddnyo.midoze.remote.requests.watchface.getWatchface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WatchfaceViewModel : ViewModel() {

    fun getWatchfaceList(): ArrayList<Watchface> {
        return runBlocking(Dispatchers.IO) {
            getWatchface("hmpace.watch.v7")
        }
    }

}