package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by joseluis on 5/11/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule = new
            IntentsTestRule<>(MainActivity.class);

    CountingIdlingResource mainActivityIdlingResource;

    @Before
    public void registerIdlingResource() {
        mainActivityIdlingResource = mainActivityIntentsTestRule.getActivity().getEspressoIdlingResourceForMainActivity();
        Espresso.registerIdlingResources(mainActivityIdlingResource);
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void checkIntent_MainActivity() {
        onData(anything()).inAdapterView(withId(R.id.recipes_grid)).atPosition(0).perform(click());
        intended(hasComponent(RecipeDetailActivity.class.getName()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mainActivityIdlingResource != null) {
            Espresso.unregisterIdlingResources(mainActivityIdlingResource);
        }
    }
}
