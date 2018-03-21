package com.example.henrikhoang.letsbake.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henrikhoang.letsbake.IngredientActivity;
import com.example.henrikhoang.letsbake.RecipeDetailsActivity;
import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.StepActivity;
import com.example.henrikhoang.letsbake.adapter.StepAdapter;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by henrikhoang
 */

public class RecipeDetailFragment extends Fragment
implements StepAdapter.StepAdapterOnClickHandler {

    private static final String tag = RecipeDetailFragment.class.getSimpleName();

    private Recipe mRecipe;

    Unbinder unbinder;

    @BindView(R.id.rv_steps_list_item)
    RecyclerView mStepsRecyclerView;

    @BindView(R.id.cake_name_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ingredient_card_view)
    CardView mCardView;

    @BindView(R.id.tv_ingredients)
    TextView mIngredientsTextView;

    private StepAdapter mStepAdapter;
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public RecipeDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_ingre_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Recipe recipe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("recipe"));
        mRecipe = recipe;
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle(recipe.getNAME());

        mStepAdapter = new StepAdapter(this, this.getContext(), mRecipe);
        mStepAdapter.setStepData(recipe);
        mStepsRecyclerView.setAdapter(mStepAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mIngredientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Link to Ingredient
                Context context = getContext();
                Class destinationClass = IngredientActivity.class;
                Intent intent = new Intent(context, destinationClass);
                intent.putExtra("recipe", Parcels.wrap(mRecipe));
                startActivity(intent);
            }
        });
        return rootView;

    }

    @Override
    public void onCLick(Bundle bundle) {
        int index = bundle.getInt("step index");

        Log.d(TAG, "STEP INDEX ACQUIRED :" + index);

        Toast.makeText(getContext(), "Step " + index + " was clicked", Toast.LENGTH_SHORT).show();
        Context context = getContext();
        Class destinationClass = StepActivity.class;
        Intent intent = new Intent(context, destinationClass);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
