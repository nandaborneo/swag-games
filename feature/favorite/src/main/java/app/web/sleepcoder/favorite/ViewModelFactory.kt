package app.web.sleepcoder.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.web.sleepcoder.core.domain.usecase.GameUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FragmentFavoriteViewModel::class.java) -> {
                FragmentFavoriteViewModel(gameUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}