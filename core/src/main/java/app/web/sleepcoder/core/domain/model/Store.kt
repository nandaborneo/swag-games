package app.web.sleepcoder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    val name: String,
    val url: String
):Parcelable
