package app.web.sleepcoder.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GameWithStores(
    @Embedded val game: GameEntity,
    @Relation(
        parentColumn = "game_id",
        entityColumn = "game_store_id"
    )
    val stores: List<StoreEntity>
)