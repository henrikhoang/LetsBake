package com.example.henrikhoang.letsbake.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

import static com.example.henrikhoang.letsbake.widget.GridWidgetService.RECIPE;

/**
 * Created by henrikhoang
 */

public class GridWidgetService extends RemoteViewsService {
    public static int RECIPE_ID;
    public static Recipe RECIPE;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }



    public static void setRecipeData(Recipe recipe) { RECIPE = recipe;}
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    private Recipe mRecipe;
    public static final String TAG = GridWidgetService.class.getSimpleName();

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe = RECIPE;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (RECIPE == null) return 0;
        return RECIPE.getINGREDIENTS().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

//        if (RECIPE == null) return null;
        Log.d(TAG, "Widget service log: " + RECIPE.getNAME());
        String ingredient = RECIPE.getINGREDIENTS().get(position).getIngredientName();
        int quantity = RECIPE.getINGREDIENTS().get(position).getQuantity();
        String measure = RECIPE.getINGREDIENTS().get(position).getMeasure();

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
