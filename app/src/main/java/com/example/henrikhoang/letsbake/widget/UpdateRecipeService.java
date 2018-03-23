package com.example.henrikhoang.letsbake.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.henrikhoang.letsbake.IdlingResource.SimpleIdlingResource;
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

    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.henrikhoang.letsbake.update_ingredients";

    public static final String ACTION_SELECTED_INGREDIENT ="com.example.henrikhoang.letsbake.update_selected_recipe";
    public static final String TAG = UpdateRecipeService.class.getSimpleName();

    private static final String ACTION_NUTELLA_PIE = "com.example.henrikhoang.letsbake.NUTELLA_PIE";
    private static final String ACTION_BROWNIES = "com.example.henrikhoang.letsbake.BROWNIES";
    private static final String ACTION_YELLOW_CAKE = "com.example.henrikhoang.letsbake.YELLOW_CAKE";
    private static final String ACTION_CHEESE_CAKE = "com.example.henrikhoang.letsbake.CHEESE_CAKE";

    public UpdateRecipeService() {super(UpdateRecipeService.class.getName());}

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NUTELLA_PIE.equals(action)) {
                handleActionUpdateRecipe(0);
            }
            if (ACTION_BROWNIES.equals(action)) {
                handleActionUpdateRecipe(1);
            }
            if (ACTION_YELLOW_CAKE.equals(action)) {
                handleActionUpdateRecipe(2);
            }
            if (ACTION_CHEESE_CAKE.equals(action)) {
                handleActionUpdateRecipe(3);
            }
        }
    }

    private void handleActionUpdateRecipe(int id) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            URL recipeRequestURL = NetworkUtility.buildURL(mContext);
            String jsonRecipeResponse = NetworkUtility.getResponseFromHttpUrl(recipeRequestURL);
            recipes = OpenRecipeJsonUtils.getRecipeFromJson(mContext, jsonRecipeResponse, new SimpleIdlingResource());
            } catch (Exception e) {
                e.printStackTrace();
            }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateAppWidget(this, appWidgetManager, recipes.get(id), appWidgetIds);
    }



    public static void startActionUpdateRecipe(Context context, String action) {
        Intent intent = new Intent(context, UpdateRecipeService.class);
        intent.setAction(action);
        context.startService(intent);
    }



}
