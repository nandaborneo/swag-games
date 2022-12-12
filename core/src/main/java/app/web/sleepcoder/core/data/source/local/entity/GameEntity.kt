package app.web.sleepcoder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "game_id")
    val gameId: String,
    @ColumnInfo(name = "slug")
    var slug: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "name_original")
    var nameOriginal: String?,
    @ColumnInfo(name = "rating")
    var rating: String,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "description_raw")
    var descriptionRaw: String?,
    @ColumnInfo(name = "released")
    var released: String,
    @ColumnInfo(name = "requirement")
    var requirement: String,
    @ColumnInfo(name = "background_image")
    val backgroundImage: String,
    @ColumnInfo(name = "background_image_additional")
    val backgroundImageAdditional: String,
    @ColumnInfo(name = "parent_platform")
    val parentPlatform: String,
    @ColumnInfo(name = "clip")
    val clip: String,
    @ColumnInfo(name = "rating_description")
    val ratingDescription: String,
    @ColumnInfo(name = "genre")
    val genre: String
)