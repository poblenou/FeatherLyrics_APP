package tk.sbarjola.pa.featherlyricsapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import tk.sbarjola.pa.featherlyricsapp.MainActivity;
import tk.sbarjola.pa.featherlyricsapp.R;
import tk.sbarjola.pa.featherlyricsapp.SplashScreenActivity;

/**
 * Created by 46465442z on 15/01/16.
 */
public class Widget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {

            int widgetId = appWidgetIds[i];

            // En caso de pulsar el widget, este ejecuta el MainActivity avisando de que está siendo ejecutado por el widget

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("alertaWidget", "widget");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.actionButton, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, views);

        }
    }
}