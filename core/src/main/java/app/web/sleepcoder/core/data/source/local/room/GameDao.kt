package app.web.sleepcoder.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import app.web.sleepcoder.core.data.source.local.entity.GameEntity
import app.web.sleepcoder.core.data.source.local.entity.GameWithStores
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM games")
    fun getAllGame(): PagingSource<Int, GameEntity>

    @Query("SELECT * FROM games where is_favorite = '1'")
    fun getFavoriteGame(): PagingSource<Int, GameEntity>

    @Transaction
    @Query("SELECT * FROM games where slug = :slugId")
    fun getDetailGame(slugId: String): Flow<GameWithStores>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(games: List<GameEntity>)

    @Update
    fun updateFavoriteGame(game: GameEntity)

    @Query("DELETE FROM games")
    suspend fun deleteAll()
}