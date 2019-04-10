package com.norvera.confession;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;
import timber.log.Timber;

/**
 * The Dialog for the {@link DatePreference}.
 *
 * @author Jakob Ulbrich
 */
public class DatePreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    /**
     * The TimePicker widget
     */
    private DatePicker datePicker;

    /**
     * Creates a new Instance of the TimePreferenceDialogFragment and stores the key of the
     * related Preference
     *
     * @param key The key of the related Preference
     * @return A new Instance of the DatePreferenceDialogFragment
     */
    public static DatePreferenceDialogFragmentCompat newInstance(String key) {
        final DatePreferenceDialogFragmentCompat
                fragment = new DatePreferenceDialogFragmentCompat();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();



        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), 0, null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker =datePickerDialog.getDatePicker();
        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            Timber.e("dsdkkl;sfkml");
        });
        return datePickerDialog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        // Use the current date as the default date in the picker

        //datePicker = view.findViewById(R.id.edit);

        // Exception: There is no TimePicker with the id 'edit' in the dialog.
        if (datePicker == null) {
            throw new IllegalStateException("Dialog view must contain a TimePicker with id 'edit'");
        }


        // Get the time from the related Preference
        Long minutesAfterMidnight = null;
        DialogPreference preference = getPreference();
        if (preference instanceof DatePreference) {
            minutesAfterMidnight = ((DatePreference) preference).getTime();
        }

    }

    /**
     * Called when the Dialog is closed.
     *
     * @param positiveResult Whether the Dialog was accepted or canceled.
     */
    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (!positiveResult) {
            // Get the current values from the TimePicker
            int dayOfMonth;
            int year;
            int monthOfYear;
                dayOfMonth = datePicker.getDayOfMonth();
                year = datePicker.getYear();
                monthOfYear = datePicker.getMonth();



            // Generate value to save
            Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);


            // Save the value
            DialogPreference preference = getPreference();
            if (preference instanceof DatePreference) {
                DatePreference datePreference = ((DatePreference) preference);
                // This allows the client to ignore the user value.
                if (datePreference.callChangeListener(calendar.getTimeInMillis())) {
                    // Save the value
                    datePreference.setTime(calendar.getTimeInMillis());
                }
            }
        }
    }
}
