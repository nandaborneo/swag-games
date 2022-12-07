package app.web.sleepcoder.favorite

import android.content.Context
import app.web.sleepcoder.swaggames.di.FavoritesModuleDependencies
import dagger.BindsInstance
import dagger.Component


@Component(
    dependencies = [FavoritesModuleDependencies::class],
)
interface FavoriteComponent {
    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(loginModuleDependencies: FavoritesModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}