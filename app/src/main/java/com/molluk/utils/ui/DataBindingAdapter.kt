package com.molluk.utils.ui

import android.graphics.Color
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import coil.load
import com.molluk.ui.home.categories.Categories
import com.resource.R

@BindingAdapter("loadImageFromUrl", requireAll = false)
fun ImageView.loadImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.load(url)
        {
            crossfade(true)
            placeholder(R.drawable.timer)
            error(R.drawable.timer_off)
        }
    }
}

fun NestedScrollView.scrollToTop() {
    this.customScrollTo(0, 0)
}

fun NestedScrollView.customScrollTo(x: Int, y: Int) {
    this.post {
        fling(0)
        smoothScrollTo(x, y, 500)
    }
}

@BindingAdapter("link")
fun TextView.link(link: String) {
    this.autoLinkMask = Linkify.WEB_URLS
    this.isClickable = true
    this.movementMethod = LinkMovementMethod.getInstance()
    this.text = HtmlCompat.fromHtml(link, HtmlCompat.FROM_HTML_MODE_COMPACT)
    this.setLinkTextColor(Color.CYAN)
    this.setTextColor(Color.CYAN)
    this.ellipsize = TextUtils.TruncateAt.MIDDLE
}

@BindingAdapter("showEpisodeTitle")
fun TextView.showEpisodeTitle(link: String) {
    val episode = Categories.Episode.name
    val newLink = link.substring(link.lastIndexOf(episode.lowercase()), link.length)
        .replace("/", " ").replace(episode.lowercase(), episode)
    this.text = newLink
}