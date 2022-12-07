package app.web.sleepcoder.core.data.source.local

import androidx.paging.PagingSource
import app.web.sleepcoder.core.data.source.local.entity.*
import app.web.sleepcoder.core.data.source.local.room.GameDao
import app.web.sleepcoder.core.data.source.local.room.RemoteKeysDao
import app.web.sleepcoder.core.data.source.local.room.StoreDao
import app.web.sleepcoder.core.utils.DataMapper.asFavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val gameDao: GameDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val storeDao: StoreDao
) {

    fun getAllGame(): PagingSource<Int, GameEntity> = gameDao.getAllGame()

    fun getFavoriteGame(): PagingSource<Int, FavoriteEntity> = gameDao.getFavoriteGame()

    fun getDetailGame(slug: String): Flow<GameWithStores?> = gameDao.getDetailGame(slug)

    suspend fun insertGame(tourismList: List<GameEntity>) = gameDao.insertGame(tourismList)

    suspend fun deleteAllGame() {
        gameDao.deleteAll()
    }

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        if (newState)
            gameDao.updateFavoriteGame(game.asFavoriteEntity)
        else
            gameDao.removeFavoriteGame(game.slug)
    }

    suspend fun getFavorite(slug: String) = gameDao.getFavoriteBySlug(slug)

    suspend fun insertKey(remoteKey: List<RemoteKeys>) {
        remoteKeysDao.insertAll(remoteKey)
    }

    suspend fun getRemoteKeysId(id: String): RemoteKeys? =
        remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() {
        remoteKeysDao.deleteRemoteKeys()
    }

    suspend fun insertStores(stores: List<StoreEntity>) {
        storeDao.insertAll(stores)
    }

    suspend fun deleteStores() {
        storeDao.deleteStores()
    }


}