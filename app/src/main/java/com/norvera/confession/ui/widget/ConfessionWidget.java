package com.norvera.confession.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.norvera.confession.MainActivity;
import com.norvera.confession.R;
import com.norvera.confession.utils.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class ConfessionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.confession_widget);
        //views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.date_of_next_confession));

        Intent settingIntent = new Intent(context, MainActivity.class);
        settingIntent.putExtra(Constants.WIDGET_EXTRA_KEY, Constants.WIDGET_SETTING_VALUE);
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        settingIntent.setAction(Constants.WIDGET_EXTRA_KEY);
        settingIntent.setData(Uri.parse(settingIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent settingPendingIntent = PendingIntent.getActivity(context, 0, settingIntent, 0);
        //views.setOnClickPendingIntent(R.id.container, configPendingIntent);
        views.setOnClickPendingIntent(R.id.btn_alarm, settingPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            //ConfessionWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

