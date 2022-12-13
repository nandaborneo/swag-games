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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SwagGameDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("swaggames".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            SwagGameDatabase::class.java, "swaggame.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory).build()
    }

    @Provides
    fun provideGameDao(database: SwagGameDatabase): GameDao = database.gameDao()

    @Provides
    fun provideRemoteKeyDao(database: SwagGameDatabase): RemoteKeysDao = database.remoteKeysDao()

    @Provides
    fun provideStoreDao(database: SwagGameDatabase): StoreDao = database.storeDao()
}