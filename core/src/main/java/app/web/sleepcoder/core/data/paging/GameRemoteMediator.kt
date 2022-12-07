package app.web.sleepcoder.core.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import app.web.sleepcoder.core.data.source.local.LocalDataSource
import app.web.sleepcoder.core.data.source.local.entity.GameEntity
import app.web.sleepcoder.core.data.source.local.entity.RemoteKeys
import app.web.sleepcoder.core.data.source.remote.RemoteDataSource
import app.web.sleepcoder.core.data.source.remote.network.ApiResponse
import app.web.sleepcoder.core.utils.AppExecutors
import app.web.sleepcoder.core.utils.DataMapper.asDatabaseLayer
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator constructor(
    private val search: String = "",
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : RemoteMediator<Int, GameEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }
            val request = if (search.isBlank())
                remoteDataSource.getPopularGame(
                    page = page,
                )
            else
                remoteDataSource.getSearchGame(
                    search = search,
                    page = page,
                )

            var endOfPaging = false
            when (val response = request.first()) {
                is ApiResponse.Success -> {
                    endOfPaging = response.data.isEmpty()

                    val listGame = response.data.map { it.asDatabaseLayer }
                    withContext(appExecutors.diskIO().asCoroutineDispatcher()) {
                        if (loadType == LoadType.REFRESH) {
                            localDataSource.deleteRemoteKeys()
                            localDataSource.deleteStores()
                            localDataSource.deleteAllGame()
                        }

                        val prevKey = if (page == 1) null else page - 1
                        val nextKey = if (endOfPaging) null else page + 1
                        val keys = listGame.map {
                            RemoteKeys(id = it.gameId, prevKey = prevKey, nextKey = nextKey)
                        }

                        localDataSource.insertKey(keys)
                        localDataSource.insertGame(listGame)
                    }
                }
                is ApiResponse.Empty -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                is ApiResponse.Error -> {
                    return MediatorResult.Error(Exception(response.errorMessage))
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaging)
        } catch (e: Exception) {
            Log.e("error", " ${e.message} => ${e.printStackTrace()}")
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.gameId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.gameId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.gameId?.let { id ->
                localDataSource.getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}