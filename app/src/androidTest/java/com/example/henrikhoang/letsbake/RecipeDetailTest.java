package com.example.henrikhoang.letsbake;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by henrikhoang
 */

@RunWith(AndroidJUnit4.class)
public class RecipeDetailTest {

    public static final String RECIPE_NAME = "Nutella Pie";
    public static final String TEST = "Tap Any Where To Start";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    @Rule
    public ActivityTestRule<RecipeDetailsActivity> secondActivityTestRule
            = new ActivityTestRule<>(RecipeDetailsActivity.class);

    private IdlingResource mIdlingResource1;
    private IdlingResource mIdlingResource2;


    @Before
    public void init() {
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }

    @Before
    public void registerIdlingResource() {
        mIdlingResource1 = mActivityTestRule.getActivity().getIdlingResource();
        mIdlingResource2 = secondActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource1);
        Espresso.registerIdlingResources(mIdlingResource2);
    }

    @Test
    public void recipeDataLoad() {
        onView(withId(R.id.rv_recipe_list_item)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));
    }

    @Test
    public void clickIngredientButton_OpenIngredientsDetail() {
        onView(withId(R.id.rv_recipe_list_item)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, CustomViewAction.clickChildViewWithId(R.id.tv_recipe_name)));
        onView(withText(TEST)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource1 != null) {
            Espresso.unregisterIdlingResources(mIdlingResource1);
        }
        if (mIdlingResource2 != null) {
            Espresso.unregisterIdlingResources(mIdlingResource2);
        }
    }
}
