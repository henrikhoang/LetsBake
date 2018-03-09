package com.example.henrikhoang.letsbake.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.utility.NetworkUtility;
import com.example.henrikhoang.letsbake.utility.OpenRecipeJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.henrikhoang.letsbake.widget.GridWidgetService.RECIPE_ID;

/**
 * Created by henrikhoang
 */

public class GridWidgetService extends RemoteViewsService {
    public static int RECIPE_ID;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    public static void setRecipeId(int id) {
        RECIPE_ID = id;
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<Recipe> mRecipes;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            URL recipeRequestURL = NetworkUtility.buildURL(mContext);
            String jsonRecipeResponse = NetworkUtility.getResponseFromHttpUrl(recipeRequestURL);
            recipes = OpenRecipeJsonUtils.getRecipeFromJson(mContext, jsonRecipeResponse);
            mRecipes = recipes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mRecipes == null || mRecipes.size() == 0) return null;

        String ingredient = recipes.get(RECIPE_ID).getINGREDIENTS().get(position).getIngredientName();
        int quantity = recipes.get(RECIPE_ID).getINGREDIENTS().get(position).getQuantity();
        String measure = recipes.get(RECIPE_ID).getINGREDIENTS().get(position).getMeasure();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_support_grid_view);
        views.setTextViewText(R.id.tv_widget_ingredient_name, ingredient);
        views.setTextViewText(R.id.tv_widget_quantity, quantity + " " + measure);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
