package com.example.henrikhoang.letsbake;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.henrikhoang.letsbake.adapter.RecipesListAdapter;
import com.example.henrikhoang.letsbake.utility.NetworkUtility;
import com.example.henrikhoang.letsbake.utility.OpenRecipeJsonUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RecipesListAdapter.RecipesListAdapterOnClickHandler,
LoaderManager.LoaderCallbacks<List<Recipe>>,
        SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;
    private RecipesListAdapter mRecipeAdapter;
    private static final int RECIPE_LOADER_ID = 1231;

    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_recipes_list);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipesListAdapter(this, this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        int loaderId = RECIPE_LOADER_ID;
        LoaderManager.LoaderCallbacks<List<Recipe>> callbacks = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callbacks);
        Log.d(TAG, "onCreate: registering preference changed listener");
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
       PREFERENCES_HAVE_BEEN_UPDATED = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.d(TAG, "onStart: preferences were updated");
            getSupportLoaderManager().restartLoader(RECIPE_LOADER_ID, null, this);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            List<Recipe> recipes = null;

            @Override
            protected  void onStartLoading() {
                if (recipes != null) {
                    deliverResult(recipes);
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }
            @Override
            public List<Recipe> loadInBackground() {
                try {
                    URL recipeRequestURL = NetworkUtility.buildURL(MainActivity.this);
                     String jsonRecipeResponse = NetworkUtility.getResponseFromHttpUrl(recipeRequestURL);
                     List<Recipe> recipes = OpenRecipeJsonUtils.getRecipeFromJson(MainActivity.this,
                             jsonRecipeResponse);
                    return recipes;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Recipe> data) {
                recipes = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecipeAdapter.setRecipeData(data);
        if (null == data) {
            return;
        } else {
            showRecipeDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    @Override
    public void onClick(Recipe recipe) {

    }

    public void invalidateData() { mRecipeAdapter.setRecipeData(null);}

    private void showRecipeDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
