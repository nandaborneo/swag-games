package app.web.sleepcoder.core.domain.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import app.web.sleepcoder.core.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    var gameId: String = "",
    var slug: String = "",
    var name: String = "",
    var nameOriginal: String = "",
    var rating: String = "",
    var description: String = "",
    var descriptionRaw: String = "",
    var released: String = "",
    var isFavorite: Boolean = false,
    var requirement: String = "",
    var backgroundImage: String = "",
    var backgroundImageAdditional: String = "",
    var parentPlatform: String = "",
    var clip: String = "",
    var store: List<Store> = arrayListOf(),
    var genre: String = "",
    var ratingDescription: String = ""
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

    fun getDrawablePlatform(context: Context):  Drawable? =
        if (parentPlatform.contains(other = "pc", ignoreCase = true)) {
            ContextCompat.getDrawable(context,R.drawable.ic_platform_windows)
        } else if (parentPlatform.contains(other = "playstation", ignoreCase = true)) {
            ContextCompat.getDrawable(context,R.drawable.ic_platform_ps)
        } else if (parentPlatform.contains(other = "xbox", ignoreCase = true)) {
            ContextCompat.getDrawable(context,R.drawable.ic_platform_xbox)
        } else if (parentPlatform.contains(other = "android", ignoreCase = true)) {
            ContextCompat.getDrawable(context,R.drawable.ic_platform_android)
        } else if (parentPlatform.contains(other = "ios", ignoreCase = true)) {
            ContextCompat.getDrawable(context,R.drawable.ic_platform_ios)
        }else{
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_image_not_supported_24)
        }

}