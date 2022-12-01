package app.web.sleepcoder.core.data.source.remote.response

data class ListGameResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<GameResponse>,
)