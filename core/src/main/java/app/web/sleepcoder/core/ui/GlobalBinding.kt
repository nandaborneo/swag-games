package app.web.sleepcoder.core.ui

import android.graphics.drawable.Drawable
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

    @BindingAdapter("setIconified")
    @JvmStatic
    fun setIconified(view: TextView, game: Game?) {
        game?.let {
            view.text = game.name
            game.getDrawableRating?.let { it1 -> view.setIconified(it.name, it1) }
        }
    }

    @BindingAdapter("loadImageDrawable")
    @JvmStatic
        fun loadImageDrawable(imageView: ImageView, resource: Int?) {
        resource?.let {
            imageView.load(resource, object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    imageView.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        }
    }

}