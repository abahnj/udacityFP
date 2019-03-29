package com.norvera.confession.utils

import android.icu.text.MessageFormat
import android.os.Build
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.norvera.confession.data.models.CommandmentEntry


@BindingAdapter("commandmentText")
fun commandmentText(textView: TextView, commandmentEntry: CommandmentEntry) {
    if (commandmentEntry.id < 11)
        textView.text = commandmentEntry.text
    else
        textView.text = commandmentEntry.commandment
}


@BindingAdapter("ordinalText")
fun ordinalText(textView: TextView, number: Number) {
    textView.text = getNumberOrdinal(number)
}

private fun getNumberOrdinal(number: Number?): String? {
    if (number == null) return null

    var format = "Sins against the Ministry"
    if (number.toInt() < 11) {
        format = "Sins against the {0,spellout,%spellout-ordinal} commandment"
    }


    //todo move to string resource

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        MessageFormat.format(format, number)
    } else {
        "com.ibm.icu.text.MessageFormat.format(format, number)"
    }
}

