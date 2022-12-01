package app.web.sleepcoder.core.data.source.local

import androidx.paging.PagingSource
import app.web.sleepcoder.core.data.source.local.entity.GameEntity
import app.web.sleepcoder.core.data.source.local.entity.GameWithStores
import app.web.sleepcoder.core.data.source.local.entity.RemoteKeys
import app.web.sleepcoder.core.data.source.local.entity.StoreEntity
import app.web.sleepcoder.core.data.source.local.room.GameDao
import app.web.sleepcoder.core.data.source.local.room.RemoteKeysDao
import app.web.sleepcoder.core.data.source.local.room.StoreDao
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

    fun getFavoriteGame(): PagingSource<Int, GameEntity> = gameDao.getFavoriteGame()

    fun getDetailGame(slug: String): Flow<GameWithStores> = gameDao.getDetailGame(slug)

    suspend fun insertGame(tourismList: List<GameEntity>) = gameDao.insertGame(tourismList)

    suspend fun deleteAllGame(){
        gameDao.deleteAll()
    }

    fun setFavoriteGame(tourism: GameEntity, newState: Boolean) {
        tourism.isFavorite = newState
        gameDao.updateFavoriteGame(tourism)
    }

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

    suspend fun deleteStores(){
        storeDao.deleteStores()
    }




}