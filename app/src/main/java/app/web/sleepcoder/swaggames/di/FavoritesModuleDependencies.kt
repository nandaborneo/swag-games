package app.web.sleepcoder.swaggames.di

import app.web.sleepcoder.core.domain.usecase.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoritesModuleDependencies {
    fun gameUseCase(): GameUseCase
}