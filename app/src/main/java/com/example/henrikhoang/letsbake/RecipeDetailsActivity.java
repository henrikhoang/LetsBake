package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.henrikhoang.letsbake.fragment.IngredientsFragment;
import com.example.henrikhoang.letsbake.fragment.RecipeDetailFragment;
import com.example.henrikhoang.letsbake.fragment.RecipeThumbnailFragment;
import com.example.henrikhoang.letsbake.fragment.StepFragment;

import org.parceler.Parcels;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements
 RecipeThumbnailFragment.OnImageClickListener, RecipeDetailFragment.OnDataItemClickListener
{

    public static int mStepIndex;
    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    public static boolean mTwoPane;

    private static boolean initialOpen;
    private static Recipe mRecipe;
    private static List<Step> mSteps;


    public static void setData(int stepIndex) {
        mStepIndex = stepIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mRecipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
        mSteps = mRecipe.getSTEPS();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (null == savedInstanceState) { initialOpen = true; }
        else if (null != savedInstanceState) { initialOpen = false; }

        //For tablet
        if (findViewById(R.id.divider_line) != null) {
            mTwoPane = true;
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.details_list_fragment, recipeDetailFragment)
                    .commit();

        }
        else if (findViewById(R.id.divider_line) == null) {
            mTwoPane = false;
            RecipeThumbnailFragment recipeThumbnailFragment = new RecipeThumbnailFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.ingre_step_fragment, recipeThumbnailFragment)
                    .commit();
        }

        Log.d(TAG, "TWO  PANE = " + mTwoPane);
    }

    public static boolean isTwoPane() {
        return mTwoPane;
    }

    @Override
    public void onImageClicked() {
        if (!mTwoPane) {
            RecipeDetailFragment newFragment = new RecipeDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingre_step_fragment, newFragment)
                    .commit();
        }
    }

    @Override
    public void onIngredientsClicked() {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, ingredientsFragment)
                    .commit();
        }
    }

    @Override
    public void onStepsClicked() {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepFragment stepFragment = new StepFragment();
            stepFragment.updateDataStep(mSteps.get(mStepIndex));

            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, stepFragment)
                    .commit();
        }
    }
}
