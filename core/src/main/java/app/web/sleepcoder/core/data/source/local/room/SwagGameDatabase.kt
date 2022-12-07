package app.web.sleepcoder.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import app.web.sleepcoder.core.data.source.local.entity.FavoriteEntity
import app.web.sleepcoder.core.data.source.local.entity.GameEntity
import app.web.sleepcoder.core.data.source.local.entity.RemoteKeys
import app.web.sleepcoder.core.data.source.local.entity.StoreEntity

@Database(
    entities = [GameEntity::class, RemoteKeys::class, StoreEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)

abstract class SwagGameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun storeDao(): StoreDao
}