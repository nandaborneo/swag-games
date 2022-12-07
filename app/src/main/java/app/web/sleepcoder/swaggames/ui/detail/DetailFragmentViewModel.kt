package app.web.sleepcoder.swaggames.ui.detail

import androidx.lifecycle.*
import app.web.sleepcoder.core.data.Resource
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModel() {

    val slug = MutableLiveData("")
    val game = MutableLiveData<Game>()
    val message = MutableLiveData("")
    val detailGameResource = slug.switchMap {
        if (it.isNullOrBlank()) return@switchMap liveData<Resource<Game>> { }
        gameUseCase.getDetailGame(it).asLiveData()
    }

    fun setFavorite() {
        game.value?.let {
            gameUseCase.setFavoriteGame(
                game = it,
                !it.isFavorite
            )
            slug.value = "${slug.value}"
        }
    }
}