package app.web.sleepcoder.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ParentPlatform(
    @SerializedName("platform")
    val platform: PlatformDetail
)