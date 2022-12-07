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
    val isLoading = MutableLiveData(false)
    val skipFirstFetchLoading = MutableLiveData(false)

    val listGames =
        if (skipFirstFetchLoading.value == true) liveData { }
        else query.switchMap {
            Log.e("log","query.switchMap => called")
            if (it.isNullOrBlank()) {
                gameUseCase.getPopularGame().asLiveData().cachedIn(viewModelScope)
            } else
                gameUseCase.getSearchGame(it).asLiveData().cachedIn(viewModelScope)
        }

}

