package com.norvera.confession.utils

import android.text.TextPaint
import android.text.style.RelativeSizeSpan
import androidx.annotation.ColorInt

class RelativeSizeColorSpan(
    @ColorInt private val color: Int,
    size: Float
) : RelativeSizeSpan(size) {
    override fun updateDrawState(textPaint: TextPaint?) {
        super.updateDrawState(textPaint!!)
        textPaint.color = color
    }
}