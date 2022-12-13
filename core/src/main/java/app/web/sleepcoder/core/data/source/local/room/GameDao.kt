package app.web.sleepcoder.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import app.web.sleepcoder.core.data.source.local.entity.FavoriteEntity
import app.web.sleepcoder.core.data.source.local.entity.GameEntity
import app.web.sleepcoder.core.data.source.local.entity.GameWithStores
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    fun getAllGame(): PagingSource<Int, GameEntity>

    @Query("SELECT * FROM favorites")
    fun getFavoriteGame(): PagingSource<Int, FavoriteEntity>

    @Transaction
    @Query("SELECT * FROM games where slug = :slugId")
    fun getDetailGame(slugId: String): Flow<GameWithStores?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(games: List<GameEntity>)

    @Update
    suspend fun updateDetailGame(games: GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteGame(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites where slug = :slugId")
    fun removeFavoriteGame(slugId: String)

    @Query("SELECT * FROM favorites where slug = :slugId")
    suspend fun getFavoriteBySlug(slugId: String): FavoriteEntity?

    @Query("DELETE FROM games")
    suspend fun deleteAll()
}