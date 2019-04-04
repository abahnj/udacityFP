package com.norvera.confession.utils

import android.text.format.DateUtils.DAY_IN_MILLIS

object TimeAgo {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS

    fun getTimeAgo(time: Long): String? {

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        val diff = now - time

        val days = diff / DAY_IN_MILLIS
        val weeks = days / 7
        val months = days / 31
        val years = days / 365

        return when {
            diff < 48 * HOUR_MILLIS -> "yesterday"
            diff < WEEK_IN_MILLIS -> "$days days"
            diff < 7 * 30 * DAY_IN_MILLIS -> "$weeks weeks"
            diff < 7 * 30 * 12 -> "$months months"
            else -> "$years  years"
        }
    }
}