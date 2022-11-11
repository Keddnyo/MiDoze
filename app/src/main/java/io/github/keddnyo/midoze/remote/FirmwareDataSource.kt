package io.github.keddnyo.midoze.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.repository.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.remote.requests.getFirmwareList

class FirmwareDataSource: PagingSource<Int, Firmware>() {

    override fun getRefreshKey(state: PagingState<Int, Firmware>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Firmware> {
        val maxSize = deviceList.lastIndex
        val key = params.key ?: 0
        val response = getFirmwareList(index = key)

        return try {
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (key == maxSize) null else key + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}