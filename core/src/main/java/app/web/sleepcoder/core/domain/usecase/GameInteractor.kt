package app.web.sleepcoder.core.domain.usecase

import androidx.paging.PagingData
import app.web.sleepcoder.core.data.Resource
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gameRepository: IGameRepository): GameUseCase {

    override fun getPopularGame(): Flow<PagingData<Game>> = gameRepository.getPopularGame()

    override fun getSearchGame(search: String): Flow<PagingData<Game>> = gameRepository.getSearchGame(search)

    override fun getDetailGame(slug: String): Flow<Resource<Game>> = gameRepository.getDetailGame(slug)

    override fun getFavoriteGame(): Flow<PagingData<Game>> = gameRepository.getFavoriteGame()

    override fun setFavoriteGame(game: Game, state: Boolean)  = gameRepository.setFavoriteGame(game, state)
}