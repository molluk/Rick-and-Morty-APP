package com.molluk.utils.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.resource.R

@BindingAdapter("loadImageFromUrl", requireAll = false)
fun ImageView.loadImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.load(url)
        {
            crossfade(true)
            //загрузка
            placeholder(R.drawable.timer)
            //ошибка
            error(R.drawable.timer_off)
        }
    }
}