package com.example.henrikhoang.letsbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    public static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static final String ACTION_NUTELLA_PIE = "com.example.henrikhoang.letsbake.NUTELLA_PIE";


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<Recipe> recipes,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        Intent adapterIntent = new Intent(context, GridWidgetService.class);

        views.setTextViewText(R.id.button_nutellaPie, context.getResources().getString(R.string.nutella_pie));
        Log.d(TAG, "CALLING WIDGET BUTTON: " + context.getResources().getString(R.string.nutella_pie));

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, adapterIntent , 0);
        views.setOnClickPendingIntent(R.id.button_nutellaPie, pendingIntent);

        views.setTextViewText(R.id.button_brownies, context.getResources().getString(R.string.brownies));
        views.setTextViewText(R.id.button_cheeseCake, context.getResources().getString(R.string.cheese_cake));
        views.setTextViewText(R.id.button_yellowCake, context.getResources().getString(R.string.yellow_cake));

        views.setRemoteAdapter(R.id.widget_grid_view, adapterIntent);
        GridWidgetService.setRecipeData(recipes);

        views.setEmptyView(R.id.widget_grid_view, R.id.empty_widget_layout);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        UpdateRecipeService.startActionUpdateRecipe(context);

    }

    public static void updateRecipeIngredients(Context context, AppWidgetManager appWidgetManager,
                                               List<Recipe> recipes, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipes, appWidgetId);
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
