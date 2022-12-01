package app.web.sleepcoder.core.di

import app.web.sleepcoder.core.data.GamesRepository
import app.web.sleepcoder.core.domain.repository.IGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gamesRepository: GamesRepository): IGameRepository

}