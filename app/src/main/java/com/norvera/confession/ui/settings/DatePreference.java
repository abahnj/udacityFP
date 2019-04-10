package com.norvera.confession.ui.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.norvera.confession.R;

import androidx.preference.DialogPreference;

/**
 * A Preference to select a specific Time with a {@link android.widget.TimePicker}.
 *
 * @author Jakob Ulbrich
 */
public class DatePreference extends DialogPreference {

    /**
     * In Minutes after midnight
     */
    private long mTime;

    /**
     * Resource of the dialog layout
     */
    //private int mDialogLayoutResId = R.layout.pref_dialog_date;

    public DatePreference(Context context) {
        // Delegate to other constructor
        this(context, null);
    }

    public DatePreference(Context context, AttributeSet attrs) {
        // Delegate to other constructor
        // Use the preferenceStyle as the default style
        this(context, attrs, R.attr.preferenceStyle);
    }

    public DatePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        // Delegate to other constructor
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public DatePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // Du custom stuff here
        // ...
        // read attributes etc.
    }

    /**
     * Gets the time from the Shared Preferences
     *
     * @return The current preference value
     */
    public long getTime() {
        return mTime;
    }

    /**
     * Saves the time to the SharedPreferences
     *
     * @param time The time to save
     */
    public void setTime(long time) {
        mTime = time;
        // Save to SharedPreference
        persistLong(time);
        notifyChanged();
    }

    //

    /**
     * Called when a Preference is being inflated and the default value attribute needs to be read
     */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // The type of this preference is Int, so we read the default value from the attributes
        // as Int. Fallback value is set to 0.
        return a.getInt(index, 0);
    }

    //

    /**
     * Returns the layout resource that is used as the content View for the dialog
     */
    @Override
    public int getDialogLayoutResource() {
        return 1;
    }


}

