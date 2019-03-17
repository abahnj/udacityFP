package com.norvera.confession.utils;

import android.icu.text.MessageFormat;
import android.os.Build;
import android.widget.TextView;

import com.norvera.confession.data.models.CommandmentEntry;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("commandmentText")
    public static void commandmentText(TextView textView, CommandmentEntry commandmentEntry) {
        if (commandmentEntry.commandment != null ) {
            if (commandmentEntry.id < 11)
                textView.setText(commandmentEntry.text);
            else
                textView.setText(commandmentEntry.commandment);
        }
    }


    @BindingAdapter("ordinalText")
    public static void ordinalText(TextView textView, Number number) {
        textView.setText(getNumberOrdinal(number));
    }

    private static String getNumberOrdinal(Number number) {
        String format;
        if (number == null) {
            return null;
        }
        if (number.intValue() < 11){
           format = "Sins against the {0,spellout,%spellout-ordinal} commandment";
        } else {
            format = "Sins against the Ministry";
        }


        //todo move to string resource

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return MessageFormat.format(format, number);
        } else {
            return "com.ibm.icu.text.MessageFormat.format(format, number)";
        }
    }
}
