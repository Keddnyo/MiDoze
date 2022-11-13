package io.github.keddnyo.midoze.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.keddnyo.midoze.local.repositories.deviceList
import io.github.keddnyo.midoze.remote.models.firmware.Firmware
import io.github.keddnyo.midoze.remote.requests.firmware.getFirmwareList
import java.net.ConnectException
import java.net.UnknownHostException

class FirmwarePagingSource: PagingSource<Int, Firmware>() {

    override fun getRefreshKey(state: PagingState<Int, Firmware>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Firmware> {
        val maxSize = deviceList.lastIndex
        val key = params.key ?: 0

        return try {
            val response = getFirmwareList(i = key)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (key == maxSize) null else key + 1
            )
        } catch (e: ConnectException) {
            LoadResult.Error(
                throwable = Throwable(
                    message = "Can't connect to remote host",
                )
            )
        } catch (e: UnknownHostException) {
            LoadResult.Error(
                throwable = Throwable(
                    message = "The remote host is unreachable",
                )
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}