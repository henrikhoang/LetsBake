package com.example.henrikhoang.letsbake.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.utility.NetworkUtility;
import com.example.henrikhoang.letsbake.utility.OpenRecipeJsonUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by henrikhoang
 */

public class UpdateRecipeService extends IntentService {

    private static Context mContext;

    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.henrikhoang.letsbake.";

    public static final String TAG = UpdateRecipeService.class.getSimpleName();

    public UpdateRecipeService() {super(UpdateRecipeService.class.getName());}

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                handleActionUpdateRecipe();
            }
        }
    }

    private void handleActionUpdateRecipe() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            URL recipeRequestURL = NetworkUtility.buildURL(mContext);
            String jsonRecipeResponse = NetworkUtility.getResponseFromHttpUrl(recipeRequestURL);
            recipes = OpenRecipeJsonUtils.getRecipeFromJson(mContext, jsonRecipeResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeIngredients(this, appWidgetManager, recipes, appWidgetIds);
    }



    public static void startActionUpdateRecipe(Context context) {
        Intent intent = new Intent(context, UpdateRecipeService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }



}
