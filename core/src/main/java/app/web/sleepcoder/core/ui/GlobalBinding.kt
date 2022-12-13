package app.web.sleepcoder.core.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.web.sleepcoder.core.domain.model.Game
import app.web.sleepcoder.core.utils.load
import app.web.sleepcoder.core.utils.setIconified
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object GlobalBinding {

    @BindingAdapter("loadImageUrl")
    @JvmStatic
    fun loadImageUrl(view: ImageView, url: String?) {
        url?.let {
            view.load(it)
        }
    }

    @BindingAdapter("setGameNameWithIcon")
    @JvmStatic
    fun setGameNameWithIcon(view: TextView, game: Game?) {
        game?.let {
            view.text = game.name
            game.getDrawableRating?.let { it1 -> view.setIconified(it.name, it1) }
        }
    }

    @BindingAdapter("setIconByRatingDescription")
    @JvmStatic
    fun setIconByRatingDescription(view: TextView, game: Game?) {
        game?.let {
            view.text = game.ratingDescription
            game.getDrawableRating?.let { it1 -> view.setIconified(it.ratingDescription, it1) }
        }
    }

    @Suppress("DEPRECATION")
    @BindingAdapter("textFromHtmlFormat")
    @JvmStatic
    fun setTextFromHtmlFormat(view: TextView, game: String?) {
        game?.let {
            view.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(it)
            }
        }
    }

}