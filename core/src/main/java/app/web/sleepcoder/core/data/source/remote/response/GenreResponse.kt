package app.web.sleepcoder.core.data.source.remote.response

data class GenreResponse(
    val games_count: Int,
    val id: Int,
    val image_background: String,
    val name: String,
    val slug: String
)