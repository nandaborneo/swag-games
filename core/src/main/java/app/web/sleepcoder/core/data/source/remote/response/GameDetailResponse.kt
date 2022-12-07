package app.web.sleepcoder.core.data.source.remote.response

data class GameDetailResponse(
    val background_image: String,
    val background_image_additional: String,
    val clip: ClipResponse?,
    val description: String,
    val description_raw: String,
    val genres: List<GenreResponse>,
    val id: Int,
    val name: String,
    val name_original: String,
    val parent_platforms: List<ParentPlatform>,
    val platforms: List<PlatformRequirement>,
    val rating: Double,
    val rating_top: Int,
    val ratings: List<Rating>,
    val released: String,
    val slug: String,
    val stores: List<StoreResponse>,
    val updated: String,
)