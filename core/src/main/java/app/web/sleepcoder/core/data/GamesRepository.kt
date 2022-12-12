package app.web.sleepcoder.core.data

import androidx.paging.*
import app.web.sleepcoder.core.data.paging.GameRemoteMediator
import app.web.sleepcoder.core.data.source.local.LocalDataSource
import app.web.sleepcoder.core.data.source.remote.RemoteDataSource
import app.web.sleepcoder.core.data.source.remote.network.ApiResponse
import app.web.sleepcoder.core.data.source.remote.response.GameDetailResponse
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.domain.repository.IGameRepository
import app.web.sleepcoder.core.utils.AppExecutors
import app.web.sleepcoder.core.utils.DataMapper.asDatabaseLayer
import app.web.sleepcoder.core.utils.DataMapper.asGameEntity
import app.web.sleepcoder.core.utils.DataMapper.asModelLayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class GamesRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {

    override fun getPopularGame(): Flow<PagingData<Game>> = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        remoteMediator = GameRemoteMediator(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appExecutors = appExecutors
        ),
        pagingSourceFactory = { localDataSource.getAllGame() }
    ).flow.map { pagingData ->
        pagingData.map { it.asModelLayer }
    }

    override fun getSearchGame(search: String): Flow<PagingData<Game>> = Pager(
        config = PagingConfig(
            pageSize = 10
        ),
        remoteMediator = GameRemoteMediator(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appExecutors = appExecutors,
            search = search
        ),
        pagingSourceFactory = { localDataSource.getAllGame() }
    ).flow.map { pagingData ->
        pagingData.map { it.asModelLayer }
    }

    override fun getDetailGame(slug: String): Flow<Resource<Game>> =
        object : NetworkBoundResource<Game, GameDetailResponse>() {
            override fun loadFromDB(): Flow<Game> =
                localDataSource.getDetailGame(slug).map {
                    it?.asModelLayer?.apply {
                        isFavorite = localDataSource.getFavorite(slug) != null
                    } ?: Game()
                }

            override fun shouldFetch(data: Game?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<GameDetailResponse>> =
                remoteDataSource.getDetailGame(slug)

            override suspend fun saveCallResult(data: GameDetailResponse) {
                localDataSource.insertGame(arrayListOf(data.asDatabaseLayer))
                localDataSource.insertStores(data.stores.map { it.asModelLayer("${data.id}") })
            }

            override fun onFetchFailed() {
                super.onFetchFailed()
                loadFromDB()
            }
        }.asFlow()

    override fun getFavoriteGame(): Flow<PagingData<Game>> =
        Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = { localDataSource.getFavoriteGame() }
        ).flow.map { pagingData ->
            pagingData.map { it.asGameEntity.asModelLayer }
        }

    override fun setFavoriteGame(game: Game, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteGame(game.asDatabaseLayer,state)
        }
    }

}