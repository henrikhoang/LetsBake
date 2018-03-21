package com.example.henrikhoang.letsbake.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

import java.util.List;

import static com.example.henrikhoang.letsbake.widget.GridWidgetService.RECIPE_ID;
import static com.example.henrikhoang.letsbake.widget.GridWidgetService.mRecipes;

/**
 * Created by henrikhoang
 */

public class GridWidgetService extends RemoteViewsService {
    public static int RECIPE_ID;
    public static List<Recipe> mRecipes;
    public static final String ACTION_NUTELLA_PIE = "com.example.henrikhoang.letsbake.NUTELLA_PIE";
    public static final String ACTION_YELLOW_CAKE = "com.example.henrikhoang.letsbake.YELLOW_CAKE";
    public static final String ACTION_BROWNIES = "com.example.henrikhoang.letsbake.BROWNIES";
    public static final String ACTION_CHEESE_CAKE = "com.example.henrikhoang.letsbake.CHEESE_CAKE";


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    public static void dataHandler(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NUTELLA_PIE.equals(action)) {
                RECIPE_ID = 0;
            }
            if (ACTION_BROWNIES.equals(action)) {
                RECIPE_ID = 1;
            }
            if (ACTION_YELLOW_CAKE.equals(action)) {
                RECIPE_ID = 2;
            }
            if (ACTION_CHEESE_CAKE.equals(action)) {
                RECIPE_ID = 3;
            }
        }
    }

    public static void setRecipeData(List<Recipe> recipes) { mRecipes = recipes;}
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;


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

        if (mRecipes == null || mRecipes.size() == 0) return null;

        String ingredient = mRecipes.get(RECIPE_ID).getINGREDIENTS().get(position).getIngredientName();
        int quantity = mRecipes.get(RECIPE_ID).getINGREDIENTS().get(position).getQuantity();
        String measure = mRecipes.get(RECIPE_ID).getINGREDIENTS().get(position).getMeasure();

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
