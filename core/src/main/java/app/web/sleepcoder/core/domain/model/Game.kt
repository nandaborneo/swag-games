package app.web.sleepcoder.core.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import app.web.sleepcoder.core.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val gameId: String,
    val slug: String,
    val name: String,
    val nameOriginal: String,
    val rating: String,
    val description: String,
    val descriptionRaw: String,
    val released: String,
    val isFavorite: Boolean,
    val requirement: String,
    val backgroundImage: String,
    val backgroundImageAdditional: String,
    val parentPlatform: String,
    val clip: String,
    val store: List<Store>,
    val genre: String,
    val ratingDescription: String
) : Parcelable {

    @IgnoredOnParcel
    val getDrawableRating: Int? =
        if (ratingDescription.equals(other = "exceptional", ignoreCase = true)) {
            R.drawable.ic_rating_exceptional
        } else if (ratingDescription.equals(other = "Recommended", ignoreCase = true)) {
            R.drawable.ic_rating_recommended
        } else {
            null
        }

    @IgnoredOnParcel
    @DrawableRes
    val getDrawablePlatform:  Int? =
        if (parentPlatform.contains(other = "pc", ignoreCase = true)) {
            R.drawable.ic_platform_windows
        } else if (parentPlatform.contains(other = "playstation", ignoreCase = true)) {
            R.drawable.ic_platform_ps
        } else if (parentPlatform.contains(other = "xbox", ignoreCase = true)) {
            R.drawable.ic_platform_xbox
        } else if (parentPlatform.contains(other = "android", ignoreCase = true)) {
            R.drawable.ic_platform_android
        } else if (parentPlatform.contains(other = "ios", ignoreCase = true)) {
            R.drawable.ic_platform_ios
        } else {
            null
        }

}