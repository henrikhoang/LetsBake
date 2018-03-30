package com.example.henrikhoang.letsbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    public static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static final String ACTION_NUTELLA_PIE = "com.example.henrikhoang.letsbake.NUTELLA_PIE";
    private static final String ACTION_BROWNIES = "com.example.henrikhoang.letsbake.BROWNIES";
    private static final String ACTION_YELLOW_CAKE = "com.example.henrikhoang.letsbake.YELLOW_CAKE";
    private static final String ACTION_CHEESE_CAKE = "com.example.henrikhoang.letsbake.CHEESE_CAKE";
    public static Recipe mRecipe;

    public static void updateAppWidget(Context context, Recipe recipe) {
        mRecipe = recipe;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), RecipeWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Intent adapterIntent = new Intent(context, GridWidgetService.class);

        if (mRecipe != null) {
            views.setViewVisibility(R.id.tv_widget_loading, View.GONE);
            views.setViewVisibility(R.id.widget_progress_bar, View.GONE);
            GridWidgetService.setRecipeData(recipe);
            Log.d(TAG, mRecipe.getNAME());
            views.setRemoteAdapter(R.id.widget_grid_view, adapterIntent);
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    public static void loadingData(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), RecipeWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setViewVisibility(R.id.tv_widget_loading, View.VISIBLE);
        views.setTextViewText(R.id.tv_widget_loading, context.getResources().getString(R.string.widget_loading_data));
        views.setViewVisibility(R.id.widget_progress_bar, View.VISIBLE);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    public static void errorLoadingData(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), RecipeWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setViewVisibility(R.id.widget_progress_bar, View.GONE);
        views.setViewVisibility(R.id.tv_widget_loading, View.VISIBLE);
        views.setTextViewText(R.id.tv_widget_loading, context.getResources().getString(R.string.widget_error_loading));
        appWidgetManager.updateAppWidget(appWidgetIds, views);

    }
    protected static PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, RecipeWidgetProvider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();

        if (ACTION_NUTELLA_PIE.equals(intent.getAction())) {
            UpdateRecipeService.startActionUpdateRecipe(context, action);
        }
        if (ACTION_BROWNIES.equals(intent.getAction())) {
            UpdateRecipeService.startActionUpdateRecipe(context, action);
        }
        if (ACTION_CHEESE_CAKE.equals(intent.getAction())) {
            UpdateRecipeService.startActionUpdateRecipe(context, action);
        }
        if (ACTION_YELLOW_CAKE.equals(intent.getAction())) {
            UpdateRecipeService.startActionUpdateRecipe(context, action);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

            views.setTextViewText(R.id.button_nutellaPie, context.getResources().getString(R.string.nutella_pie));
            views.setOnClickPendingIntent(R.id.button_nutellaPie, getPendingIntent(context, ACTION_NUTELLA_PIE));

            views.setTextViewText(R.id.button_brownies, context.getResources().getString(R.string.brownies));
            views.setOnClickPendingIntent(R.id.button_brownies, getPendingIntent(context, ACTION_BROWNIES));

            views.setTextViewText(R.id.button_cheeseCake, context.getResources().getString(R.string.cheese_cake));
            views.setOnClickPendingIntent(R.id.button_cheeseCake, getPendingIntent(context, ACTION_CHEESE_CAKE));

            views.setTextViewText(R.id.button_yellowCake, context.getResources().getString(R.string.yellow_cake));
            views.setOnClickPendingIntent(R.id.button_yellowCake, getPendingIntent(context, ACTION_YELLOW_CAKE));

//            views.setEmptyView(R.id.widget_grid_view, R.id.empty_widget_layout);

            appWidgetManager.updateAppWidget(appWidgetId, views);
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
