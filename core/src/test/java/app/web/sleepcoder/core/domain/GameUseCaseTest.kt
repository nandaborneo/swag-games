package app.web.sleepcoder.core.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import app.web.sleepcoder.core.data.Resource
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.domain.repository.IGameRepository
import app.web.sleepcoder.core.domain.usecase.GameInteractor
import app.web.sleepcoder.core.domain.usecase.GameUseCase
import app.web.sleepcoder.core.ui.ListGameAdapterPaging
import app.web.sleepcoder.core.utils.DataDummy
import app.web.sleepcoder.core.utils.MainDispatcherRule
import app.web.sleepcoder.core.utils.anyObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GameUseCaseTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    private lateinit var gameUseCase: GameUseCase

    @Mock
    private lateinit var gameRepository: IGameRepository

    @Before
    fun setUp() {
        gameUseCase = GameInteractor(gameRepository)
    }

    @Test
    fun getPopularGame() = runTest {
        val dummyListGame = DataDummy.dummyListGame
        val pagingData: PagingData<Game> = GamePagingSource.snapshot(dummyListGame)
        val expectedReturn = flow { emit(pagingData) }

        `when`(gameRepository.getPopularGame()).thenReturn(expectedReturn)
        val actualReturn: PagingData<Game> = gameUseCase.getPopularGame().first()
        verify(gameRepository).getPopularGame()
        val differ = AsyncPagingDataDiffer(
            diffCallback = ListGameAdapterPaging.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualReturn)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyListGame, differ.snapshot())
        Assert.assertEquals(dummyListGame.size, differ.snapshot().size)
        Assert.assertEquals(dummyListGame[0].gameId, differ.snapshot()[0]?.gameId)
    }

    @Test
    fun getSearchGame() = runTest {
        val dummyListGame = DataDummy.dummyListGame
        val pagingData: PagingData<Game> = GamePagingSource.snapshot(dummyListGame)
        val expectedReturn = flow { emit(pagingData) }

        `when`(gameRepository.getSearchGame(anyString())).thenReturn(expectedReturn)
        val actualReturn: PagingData<Game> = gameUseCase.getSearchGame("").first()
        verify(gameRepository).getSearchGame(anyString())
        val differ = AsyncPagingDataDiffer(
            diffCallback = ListGameAdapterPaging.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualReturn)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyListGame, differ.snapshot())
        Assert.assertEquals(dummyListGame.size, differ.snapshot().size)
        Assert.assertEquals(dummyListGame[0].gameId, differ.snapshot()[0]?.gameId)
    }

    @Test
    fun getDetailGame() = runTest {
        val dummyGame = DataDummy.dummyGame(1)
        val expectedReturn = flow { emit(Resource.Success(dummyGame)) }

        `when`(gameRepository.getDetailGame(anyString())).thenReturn(expectedReturn)
        val actualReturn: Resource<Game> = gameUseCase.getDetailGame("").first()
        verify(gameRepository).getDetailGame(anyString())

        Assert.assertTrue(actualReturn is Resource.Success)
        Assert.assertEquals(dummyGame.gameId, actualReturn.data?.gameId)
    }


    @Test
    fun getFavoriteGame() = runTest {
        val dummyListGame = DataDummy.dummyListFavoriteGame
        val pagingData: PagingData<Game> = GamePagingSource.snapshot(dummyListGame)
        val expectedReturn = flow { emit(pagingData) }

        `when`(gameRepository.getFavoriteGame()).thenReturn(expectedReturn)
        val actualReturn: PagingData<Game> = gameUseCase.getFavoriteGame().first()
        verify(gameRepository).getFavoriteGame()
        val differ = AsyncPagingDataDiffer(
            diffCallback = ListGameAdapterPaging.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualReturn)


        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyListGame, differ.snapshot())
        Assert.assertEquals(dummyListGame.size, differ.snapshot().size)
        differ.snapshot().items.forEach {
            Assert.assertTrue(it.isFavorite)
        }
        Assert.assertEquals(dummyListGame[0].gameId, differ.snapshot()[0]?.gameId)
    }

    @Test
    fun setFavoriteGame() = runTest {
        val dummyGame = DataDummy.dummyGame(3)
        gameUseCase.setFavoriteGame(dummyGame, !dummyGame.isFavorite)
        verify(gameRepository,Times(1)).setFavoriteGame(anyObject(), anyBoolean())
    }

    class GamePagingSource : PagingSource<Int, LiveData<List<Game>>>() {
        companion object {
            fun snapshot(items: List<Game>): PagingData<Game> {
                return PagingData.from(items)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, LiveData<List<Game>>>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Game>>> {
            return LoadResult.Page(emptyList(), 0, 1)
        }
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}