package com.example.henrikhoang.letsbake.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.henrikhoang.letsbake.IngredientsActivity;
import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.adapter.RecipesListAdapter;
import com.example.henrikhoang.letsbake.utility.NetworkUtility;
import com.example.henrikhoang.letsbake.utility.OpenRecipeJsonUtils;

import org.parceler.Parcels;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by henrikhoang on 2/9/18.
 */

public class MenuRecipeFragment extends Fragment
        implements RecipesListAdapter.RecipesListAdapterOnClickHandler,
LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final String TAG = MenuRecipeFragment.class.getSimpleName();

    private RecipesListAdapter mRecipeAdapter;
    private static final int RECIPE_LOADER_ID =1231;

    Unbinder unbinder;

    @BindView(R.id.app_toolbar)
    Toolbar mAppToolbar;

    @BindView(R.id.rv_recipe_list_item)
    RecyclerView mRecyclerView;

    @BindView(R.id.background_cover)
    RelativeLayout mAppCover;

    @BindView(R.id.menu)
    View mAppCoverLayout;

    @BindView(R.id.backdrop)
    ImageView mBackdrop;

    private List<Recipe> recipes;

    public MenuRecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_menu, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mAppToolbar);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecipeAdapter = new RecipesListAdapter(this, this.getContext());
        mRecyclerView.setAdapter(mRecipeAdapter);
        try {
            Glide.with(this).load(R.drawable.cover).into(mBackdrop);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int loaderId = RECIPE_LOADER_ID;
        LoaderManager.LoaderCallbacks<List<Recipe>> callbacks = MenuRecipeFragment.this;
        Bundle bundleForLoader = null;
        ((AppCompatActivity)getActivity()).getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callbacks);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Recipe recipe) {
        Log.d(TAG, "YOU ARE CHOOSING: " + recipe.getSTEPS().get(2).getDescription());
        Log.d(TAG, "ID that has been sent: " + recipe.getID());

        Context context = getContext();
        Class destinationClass = IngredientsActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra("recipe", Parcels.wrap(recipe));
        startActivity(intent);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(getActivity()) {


            @Override
            protected  void onStartLoading() {
                if (recipes != null) {
                    deliverResult(recipes);
                } else {
                    mAppCover.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }
            @Override
            public List<Recipe> loadInBackground() {
                try {
                    URL recipeRequestURL = NetworkUtility.buildURL(getActivity());
                    String jsonRecipeResponse = NetworkUtility.getResponseFromHttpUrl(recipeRequestURL);
                    List<Recipe> recipes = OpenRecipeJsonUtils.getRecipeFromJson(getActivity(),
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
        mRecyclerView.setVisibility(View.VISIBLE);
        mAppCoverLayout.setVisibility(View.VISIBLE);
        mAppCover.setVisibility(View.GONE);
        mRecipeAdapter.setRecipeData(data);
        if (null == data) {
            return;
        } else {
            showRecipeDataView();
        }
    }

    private void showRecipeDataView() {
        mAppCover.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
