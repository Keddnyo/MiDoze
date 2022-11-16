package io.github.keddnyo.midoze.local.viewmodels.watchface

import androidx.lifecycle.ViewModel
import io.github.keddnyo.midoze.remote.requests.entities.watchface.getWatchface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class WatchfaceViewModel : ViewModel() {

    val watchfaceList = runBlocking(Dispatchers.IO) {
        getWatchface("hmpace.watch.v7")
    }

}