package io.github.keddnyo.midoze.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.data_models.FirmwareDataModel

class FirmwareDataSource: PagingSource<Int, FirmwareDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, FirmwareDataModel>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FirmwareDataModel> {
        val batchSize = 20
        val maxSize = 300

        return try {
            val page = params.key ?: batchSize
            // val response = TODO("Not implemented yet")
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (page != maxSize) page + batchSize else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}