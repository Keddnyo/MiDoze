package io.github.keddnyo.midoze.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.models.firmware.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.remote.requests.getFirmwareList

class FirmwareDataSource: PagingSource<Int, Firmware>() {

    override fun getRefreshKey(state: PagingState<Int, Firmware>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Firmware> {
        val maxSize = deviceList.size - 1

        val startIndex = params.key ?: 0
        val firmwareCount = params.loadSize
        val endIndex = startIndex + firmwareCount

        val response = getFirmwareList(startIndex..endIndex)

        return try {
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (endIndex >= maxSize) null else endIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}