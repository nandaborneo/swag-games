package app.web.sleepcoder.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.web.sleepcoder.core.data.source.local.entity.RemoteKeys
import app.web.sleepcoder.core.data.source.local.entity.StoreEntity

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(store: List<StoreEntity>)

    @Query("DELETE FROM stores")
    suspend fun deleteStores()
}