package com.example.henrikhoang.letsbake.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.adapter.IngredientAdapter;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by henrikhoang
 */

public class IngredientsFragment extends Fragment {

    private static final String TAG = IngredientsFragment.class.getSimpleName();

    private Recipe mRecipe;
    Unbinder unbinder;

    @BindView(R.id.ingredient_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_ingredient_list_item)
    RecyclerView mIngredientRecyclerView;

    private IngredientAdapter mIngredientAdapter;

    public IngredientsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Recipe recipe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("recipe"));
        mRecipe = recipe;
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            mToolbar.setTitle(mRecipe.getNAME());


            mIngredientAdapter = new IngredientAdapter(this.getContext(), mRecipe);
            mIngredientAdapter.setIngredientData(mRecipe);
            mIngredientRecyclerView.setAdapter(mIngredientAdapter);

            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mIngredientRecyclerView.setLayoutManager(layoutManager);
            mIngredientRecyclerView.setItemAnimator(new DefaultItemAnimator());


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
