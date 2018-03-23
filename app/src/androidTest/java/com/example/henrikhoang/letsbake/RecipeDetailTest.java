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
import static android.support.test.espresso.action.ViewActions.click;
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


    private IdlingResource mIdlingResource1;



    @Before
    public void init() {
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }

    @Before
    public void registerIdlingResource() {
        mIdlingResource1 = mActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource1);

    }

    @Test
    public void recipeDataLoad() {
        onView(withId(R.id.rv_recipe_list_item)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));
    }

    @Test
    public void OpenIngredientDetails() {
        onView(withId(R.id.rv_recipe_list_item)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, click()));
        onView(withText(TEST)).check(matches(isDisplayed()));
        onView(withId(R.id.iv_cake_backdrop)).perform(click());
        onView(withId(R.id.tv_ingredients)).perform(click());
        onView(withText("Graham Cracker crumbs")).check(matches(isDisplayed()));
    }

    @Test
    public void OpenStepDetails() {
        onView(withId(R.id.rv_recipe_list_item)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, click()));
        onView(withId(R.id.iv_cake_backdrop)).perform(click());
        onView(withId(R.id.rv_steps_list_item)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, click()));
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource1 != null) {
            Espresso.unregisterIdlingResources(mIdlingResource1);
        }
    }
}
