package io.github.keddnyo.midoze.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel
import io.github.keddnyo.midoze.remote.requests.getFirmwareList

class FirmwareDataSource: PagingSource<Int, FirmwareDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, FirmwareDataModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FirmwareDataModel> {
        val batchSize = 19
        val maxSize = 300

        val startIndex = params.key ?: 1
        val endIndex = startIndex + batchSize

        val response = getFirmwareList(startIndex, endIndex)

        return try {
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (endIndex != maxSize) endIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}