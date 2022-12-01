package app.web.sleepcoder.core.data.source.remote.response

data class ClipResponse(
    val clip: String,
    val clips: Clips,
    val preview: String,
    val video: String
)