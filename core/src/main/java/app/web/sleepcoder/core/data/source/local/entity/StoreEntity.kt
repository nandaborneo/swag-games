package app.web.sleepcoder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey
    @ColumnInfo(name = "store_id")
    val storeId: String,
    @ColumnInfo(name = "game_store_id")
    val gameStoreId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String,
)