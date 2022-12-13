package app.web.sleepcoder.core.data.source.remote.response

data class PlatformRequirement(
    val platform: PlatformName?,
    val released_at: String,
    val requirements_en: Requirements,
    val requirement: Requirements
)