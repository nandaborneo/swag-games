package app.web.sleepcoder.core.data.source.remote.response

data class GameResponse(
    val added: Int,
    val background_image: String?,
    val clip: ClipResponse?,
    val genres: List<GenreResponse>,
    val id: Int,
    val name: String,
    val parent_platforms: List<ParentPlatform>,
    val platforms: List<PlatformRequirement>,
    val rating: Double,
    val rating_top: Int,
    val ratings: List<Rating>,
    val released: String?,
    val short_screenshots: List<ShortScreenshot>,
    val slug: String,
    val stores: List<StoreDetail>,
    val tags: List<Tag>,
)