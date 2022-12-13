package app.web.sleepcoder.swaggames.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import app.web.sleepcoder.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentHomeViewModel @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModel() {
    val search = MutableLiveData("")
    val query = MutableLiveData("")
    val message = MutableLiveData("")
    var skipFetching = false

    val listGames =
        query.switchMap {
            if (it.isNullOrBlank()) {
                gameUseCase.getPopularGame().asLiveData().cachedIn(viewModelScope)
            } else
                gameUseCase.getSearchGame(it).asLiveData().cachedIn(viewModelScope)
        }

}

