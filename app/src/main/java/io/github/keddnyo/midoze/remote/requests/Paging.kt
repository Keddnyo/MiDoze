package io.github.keddnyo.midoze.remote.requests

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.wearables
import io.github.keddnyo.midoze.remote.Firmware

class Paging: PagingSource<Int, Firmware>() {

    override fun getRefreshKey(state: PagingState<Int, Firmware>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Firmware> {
        val maxSize = wearables.lastIndex
        val key = params.key ?: 0

        return try {
            val response = getFirmwareList(i = key)

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