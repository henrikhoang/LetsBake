package com.example.henrikhoang.letsbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.henrikhoang.letsbake.MainActivity;
import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<Recipe> recipes,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.tv_widget_recipe_name, pendingIntent);

        Intent updatingIntent = new Intent(context, UpdateRecipeService.class);
        updatingIntent.setAction(UpdateRecipeService.ACTION_UPDATE_INGREDIENTS);
        PendingIntent updatingPendingIntent = PendingIntent.getService(context, 0, updatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_load_recipe_ingredient, updatingPendingIntent);
        views.setTextViewText(R.id.tv_widget_ingredient, recipes.get(0).getINGREDIENTS().get(0).getIngredientName());
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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

