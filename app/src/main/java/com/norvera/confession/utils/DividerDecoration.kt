package com.norvera.confession.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import android.view.View

import com.norvera.confession.ui.prayers.SectionedAdapter

import androidx.recyclerview.widget.RecyclerView

class DividerDecoration @JvmOverloads constructor(
    context: Context,
    color: Int = Color.argb(
        (255 * 0.2).toInt(),
        255,
        255,
        255
    ),
    heightDp: Float = 1f
) : RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint()
    private val mHeightDp: Int

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.color = color
        mHeightDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightDp,
            context.resources.displayMetrics
        ).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (hasDividerOnBottom(view, parent, state)) {
            outRect.set(0, 0, 0, mHeightDp)
        } else {
            outRect.setEmpty()
        }
    }

    private fun hasDividerOnBottom(
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ): Boolean {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        return (position < state.itemCount - 1 && parent.adapter!!.getItemViewType(position) != SectionedAdapter.HEADER_VIEW_TYPE
                && parent.adapter!!.getItemViewType(position + 1) != SectionedAdapter.HEADER_VIEW_TYPE)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            if (hasDividerOnBottom(view, parent, state)) {
                c.drawRect(
                    view.left.toFloat(),
                    view.bottom.toFloat(),
                    view.right.toFloat(),
                    (view.bottom + mHeightDp).toFloat(),
                    mPaint
                )
            }
        }
    }
}
