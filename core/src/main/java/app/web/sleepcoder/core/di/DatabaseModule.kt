package app.web.sleepcoder.core.di

import android.content.Context
import androidx.room.Room
import app.web.sleepcoder.core.data.source.local.room.GameDao
import app.web.sleepcoder.core.data.source.local.room.RemoteKeysDao
import app.web.sleepcoder.core.data.source.local.room.StoreDao
import app.web.sleepcoder.core.data.source.local.room.SwagGameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SwagGameDatabase =
        Room.databaseBuilder(
            context,
            SwagGameDatabase::class.java, "swaggame.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGameDao(database: SwagGameDatabase): GameDao = database.gameDao()

    @Provides
    fun provideRemoteKeyDao(database: SwagGameDatabase): RemoteKeysDao = database.remoteKeysDao()

    @Provides
    fun provideStoreDao(database: SwagGameDatabase): StoreDao = database.storeDao()
}