package app.web.sleepcoder.swaggames.di

import app.web.sleepcoder.core.domain.usecase.GameInteractor
import app.web.sleepcoder.core.domain.usecase.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideGameUseCase(gameInteractor: GameInteractor): GameUseCase

}
