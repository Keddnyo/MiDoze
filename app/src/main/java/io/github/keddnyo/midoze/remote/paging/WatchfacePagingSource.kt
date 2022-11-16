package io.github.keddnyo.midoze.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.repositories.watchface.watchfaceWearables
import io.github.keddnyo.midoze.remote.models.watchface.WatchfaceList
import io.github.keddnyo.midoze.remote.requests.entities.watchface.getWatchfaceList

class WatchfacePagingSource: PagingSource<Int, WatchfaceList>() {

    override fun getRefreshKey(state: PagingState<Int, WatchfaceList>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WatchfaceList> {
        val maxSize = watchfaceWearables.lastIndex
        val key = params.key ?: 0

        return try {
            val response = getWatchfaceList(i = key)

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