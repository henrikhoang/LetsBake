package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.henrikhoang.letsbake.adapter.IngredientAdapter;
import com.example.henrikhoang.letsbake.utility.ThumbnailUtil;

import org.parceler.Parcels;

public class IngredientsActivity extends AppCompatActivity implements
 IngredientAdapter.IngredientAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Recipe>
{

    private static final String TAG = IngredientsActivity.class.getSimpleName();
    private RecyclerView mStepsRecyclerView;

    private IngredientAdapter mIngredientAdapter;
    static int mRecipeId;
    static Recipe mRecipe;
    public static int LOADER_ID = 12321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
//        final int recipeId = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
//        mRecipeId = recipeId;
        final Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        Log.d(TAG, "PASSED TO INTENT: " + recipe.getSTEPS().get(2).getDescription());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ingredient);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        mStepsRecyclerView = (RecyclerView) findViewById(R.id.rv_steps_list_item);


        mIngredientAdapter = new IngredientAdapter(this, this);

        mStepsRecyclerView.setAdapter(mIngredientAdapter);
        mIngredientAdapter.setStepData(recipe);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int thumbnailId = ThumbnailUtil.getImageResByName(this, mRecipe.getNAME());

        try {
            Glide.with(this).load(thumbnailId).into((ImageView) findViewById(R.id.iv_cake_backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_ingredients);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_ingredients_steps);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(mRecipe.getNAME());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }



    @Override
    public void onCLick(Recipe.Steps step) {

    }

    @Override
    public Loader<Recipe> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Recipe> loader, Recipe data) {

    }

    @Override
    public void onLoaderReset(Loader<Recipe> loader) {

    }
}
