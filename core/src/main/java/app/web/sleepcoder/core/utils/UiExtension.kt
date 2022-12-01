package app.web.sleepcoder.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import app.web.sleepcoder.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.material.snackbar.Snackbar

enum class TextIconPosition {
    START,
    END
}

fun TextView.setIconified(
    text: String,
    @DrawableRes iconResId: Int,
    iconPosition: TextIconPosition = TextIconPosition.END
) {
    val image = ContextCompat.getDrawable(this.context, iconResId)
    image?.let { drawable ->
        drawable.setBounds(0, 0, this.lineHeight, this.lineHeight)
        SpannableStringBuilder("$text #").apply {
            setSpan(
                ImageSpan(drawable),
                (if (iconPosition == TextIconPosition.END) text.length else 0) + 1,
                (if (iconPosition == TextIconPosition.END) text.length else 0) + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }.let {
            setText(it)
        }
    }
}

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_image_search_24)
        .error(R.drawable.ic_baseline_image_not_supported_24)
        .into(this)
}

fun ImageView.load(@DrawableRes resource:  Int, customTarget: CustomTarget<Drawable>) {
    Glide.with(context)
        .load(resource)
        .placeholder(R.drawable.ic_baseline_image_search_24)
        .error(R.drawable.ic_baseline_image_not_supported_24)
        .into(customTarget)
}

fun String.showSnackbar(view: View) {
    if (this.isNotBlank())
        Snackbar.make(view, this, Snackbar.LENGTH_LONG).show()
}