package com.molluk.ui.base.list

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration

fun <E> MutableList<E>.toBaseList(): List<BaseListItem> {
    val list = mutableListOf<BaseListItem>()
    for (item in this)
        list.add(BaseListItem(item))
    return list
}

fun RecyclerView.setDivider() {
    val divider = MaterialDividerItemDecoration(
        context,
        DividerItemDecoration.VERTICAL
    )
    divider.dividerColor = ContextCompat.getColor(context, com.resource.R.color.color_separator)
    addItemDecoration(divider)
}

fun RecyclerView.setDivider(@DrawableRes id: Int) {
    val divider = DividerItemDecoration(
        context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = AppCompatResources.getDrawable(context,id)
    if (drawable != null) {
        divider.setDrawable(drawable)
    }
    addItemDecoration(divider)
}

fun RecyclerView.setNoBottomDivider(@DrawableRes drawableRes: Int) {
    val drawableDivider = ContextCompat.getDrawable(context, drawableRes)
    val divider = object : RecyclerView.ItemDecoration() {
        override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val dividerLeft = parent.paddingLeft
            val dividerRight = parent.width - parent.paddingRight
            val childCount = parent.childCount
            for (i in 0..childCount - 2) {
                val child: View = parent.getChildAt(i)
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                val dividerTop: Int = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + (drawableDivider?.intrinsicHeight ?: 0)
                drawableDivider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                drawableDivider?.draw(canvas)
            }
        }
    }
    addItemDecoration(divider)
}