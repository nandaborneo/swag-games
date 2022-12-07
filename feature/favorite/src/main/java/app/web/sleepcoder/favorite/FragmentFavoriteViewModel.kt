package app.web.sleepcoder.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.web.sleepcoder.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentFavoriteViewModel @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModel() {

    val listGames = gameUseCase.getFavoriteGame().asLiveData().cachedIn(viewModelScope)

}

