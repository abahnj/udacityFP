package com.norvera.confession.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

import com.norvera.confession.MainActivity
import com.norvera.confession.R
import com.norvera.confession.utils.Constants

/**
 * Implementation of App Widget functionality.
 */
class ConfessionWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            //ConfessionWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.confession_widget)
            //views.setTextViewText(R.id.appwidget_text, context.getResources().getString(R.string.date_of_next_confession));

            val settingIntent = Intent(context, MainActivity::class.java)
            settingIntent.putExtra(Constants.WIDGET_EXTRA_KEY, Constants.WIDGET_SETTING_VALUE)
            settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            settingIntent.action = Constants.WIDGET_EXTRA_KEY
            settingIntent.data = Uri.parse(settingIntent.toUri(Intent.URI_INTENT_SCHEME))

            val settingPendingIntent = PendingIntent.getActivity(context, 0, settingIntent, 0)
            //views.setOnClickPendingIntent(R.id.container, configPendingIntent);
            views.setOnClickPendingIntent(R.id.btn_alarm, settingPendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

