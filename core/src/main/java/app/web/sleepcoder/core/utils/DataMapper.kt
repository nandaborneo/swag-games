package app.web.sleepcoder.core.utils

import app.web.sleepcoder.core.data.source.local.entity.FavoriteEntity
import app.web.sleepcoder.core.data.source.local.entity.GameEntity
import app.web.sleepcoder.core.data.source.local.entity.GameWithStores
import app.web.sleepcoder.core.data.source.local.entity.StoreEntity
import app.web.sleepcoder.core.data.source.remote.response.GameDetailResponse
import app.web.sleepcoder.core.data.source.remote.response.GameResponse
import app.web.sleepcoder.core.data.source.remote.response.StoreResponse
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.domain.model.Store

object DataMapper {
    val GameEntity.asModelLayer
        get() = Game(
            gameId = this.gameId,
            slug = this.slug,
            name = this.name,
            nameOriginal = this.nameOriginal ?: "",
            rating = this.rating,
            description = this.description ?: "",
            descriptionRaw = this.descriptionRaw ?: "",
            released = this.released,
            requirement = this.requirement,
            backgroundImage = this.backgroundImage,
            backgroundImageAdditional = this.backgroundImageAdditional,
            parentPlatform = this.parentPlatform,
            clip = this.clip,
            store = arrayListOf(),
            genre = this.genre,
            ratingDescription = this.ratingDescription
        )

    val GameWithStores.asModelLayer
        get() = Game(
            gameId = this.game.gameId,
            slug = this.game.slug,
            name = this.game.name,
            nameOriginal = this.game.nameOriginal ?: "",
            rating = this.game.rating,
            description = this.game.description ?: "",
            descriptionRaw = this.game.descriptionRaw ?: "",
            released = this.game.released,
            requirement = this.game.requirement,
            backgroundImage = this.game.backgroundImage,
            backgroundImageAdditional = this.game.backgroundImageAdditional,
            parentPlatform = this.game.parentPlatform,
            clip = this.game.clip,
            store = this.stores.map { it.asModelLayer },
            ratingDescription = this.game.ratingDescription,
            genre = this.game.genre
        )

    val StoreEntity.asModelLayer
        get() = Store(
            this.name, this.url
        )

    val GameResponse.asDatabaseLayer
        get() = GameEntity(
            gameId = "${this.id}",
            slug = this.slug,
            name = this.name,
            nameOriginal = "",
            rating = "${this.rating}",
            description = "",
            descriptionRaw = "",
            released = this.released ?: "",
            requirement = this.platforms.firstOrNull() { it.platform.name.contains("PC") }?.requirements_en?.recommended
                ?: "",
            backgroundImage = this.background_image ?: "",
            backgroundImageAdditional = "",
            parentPlatform = this.parent_platforms.firstOrNull()?.platform?.name ?: "",
            clip = this.clip?.clip ?: "",
            ratingDescription = this.ratings.firstOrNull()?.title ?: "",
            genre = this.genres.joinToString(separator = ",", transform = { it.name })
        )

    fun StoreResponse.asModelLayer(gameId: String) = StoreEntity(
        storeId = "${this.id}",
        gameStoreId = gameId,
        url = this.url ?: "",
        name = this.store.name
    )

    val GameDetailResponse.asDatabaseLayer
        get() = GameEntity(
            gameId = "${this.id}",
            slug = this.slug,
            name = this.name,
            nameOriginal = this.name_original,
            rating = "${this.rating}",
            description = this.description,
            descriptionRaw = this.description_raw,
            released = this.released,
            requirement = this.platforms.firstOrNull() { it.platform.name.contains("PC") }?.requirements_en?.recommended
                ?: "",
            backgroundImage = this.background_image,
            backgroundImageAdditional = this.background_image_additional,
            parentPlatform = this.parent_platforms.firstOrNull()?.platform?.name ?: "",
            ratingDescription = this.ratings.firstOrNull()?.title ?: "",
            genre = this.genres.joinToString(separator = ",", transform = { it.name }),
            clip = this.clip?.clip ?: "",
        )

    val Game.asDatabaseLayer
        get() = GameEntity(
            gameId = this.gameId,
            slug = this.slug,
            name = this.name,
            nameOriginal = this.nameOriginal,
            rating = this.rating,
            description = this.description,
            descriptionRaw = this.descriptionRaw,
            released = this.released,
            requirement = this.requirement,
            backgroundImage = this.backgroundImage,
            backgroundImageAdditional = this.backgroundImageAdditional,
            parentPlatform = this.parentPlatform,
            clip = this.clip,
            genre = this.genre,
            ratingDescription = this.ratingDescription
        )

    val GameEntity.asFavoriteEntity
        get() = FavoriteEntity(
            gameId = this.gameId,
            slug = this.slug,
            name = this.name,
            nameOriginal = this.nameOriginal,
            rating = this.rating,
            backgroundImage = this.backgroundImage,
            backgroundImageAdditional = this.backgroundImageAdditional,
            parentPlatform = this.parentPlatform,
            ratingDescription = this.ratingDescription
        )

    val FavoriteEntity.asGameEntity
        get() = GameEntity(
            gameId = this.gameId,
            slug = this.slug,
            name = this.name,
            nameOriginal = this.nameOriginal,
            rating = this.rating,
            backgroundImage = this.backgroundImage,
            backgroundImageAdditional = this.backgroundImageAdditional,
            parentPlatform = this.parentPlatform,
            ratingDescription = this.ratingDescription,
            description = "",
            clip = "",
            descriptionRaw = "",
            genre = "",
            requirement = "",
            released = ""
        )
}