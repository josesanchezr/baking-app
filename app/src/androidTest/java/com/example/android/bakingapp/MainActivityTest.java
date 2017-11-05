package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.Matchers.anything;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by joseluis on 29/10/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    CountingIdlingResource mainActivityIdlingResource;

    @Before
    public void registerIdlingResource() {
        CountingIdlingResource mainActivityIdlingResource = mActivityTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        Espresso.registerIdlingResources(mainActivityIdlingResource);
    }

    @Test
    public void loadGridViewWithRecipes() {
        onData(anything()).inAdapterView(withId(R.id.recipes_grid)).atPosition(0).perform(click());

        onView(withId(R.id.ingredients_button)).check(matches(withText("Recipe Ingredients")));
    }

    @After
    public void unregisterIdlingResource() {
        if (mainActivityIdlingResource != null) {
            Espresso.unregisterIdlingResources(mainActivityIdlingResource);
        }
    }
}
