package app.web.sleepcoder.core.domain.repository

import androidx.paging.PagingData
import app.web.sleepcoder.core.data.Resource
import app.web.sleepcoder.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {

    fun getPopularGame(): Flow<PagingData<Game>>

    fun getSearchGame(search: String): Flow<PagingData<Game>>

    fun getDetailGame(slug: String):Flow<Resource<Game>>

    fun getFavoriteGame(): Flow<PagingData<Game>>

    fun setFavoriteGame(game: Game, state: Boolean)

}