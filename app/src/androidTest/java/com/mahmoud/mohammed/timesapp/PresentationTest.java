package com.mahmoud.mohammed.timesapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mahmoud.mohammed.timesapp.presentation.articles.TimesActivity;
import com.mahmoud.mohammed.timesapp.presentation.common.EspressoIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PresentationTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mahmoud.mohammed.timesapp", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<TimesActivity> mIntentsRule = new ActivityTestRule<>(TimesActivity.class);

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void registerIdlingResource() {
       IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }


    @Test
    public void testRecyclerView() {
        onView(withId(R.id.times_recycler)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewItem() {

        onView(withRecyclerView(R.id.times_recycler).atPositionOnView(3, R.id.title)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.times_recycler).atPositionOnView(3, R.id.created_by)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.times_recycler).atPositionOnView(3, R.id.source)).check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.times_recycler).atPositionOnView(3, R.id.date)).check(matches(isDisplayed()));
    }

    @Test
    public void testClick() {

        onView(withRecyclerView(R.id.times_recycler).atPosition(2)).perform(click());

    }

    @Test
    public void testDetailsFragment() {

        onView(withRecyclerView(R.id.times_recycler).atPosition(2)).perform(click());
        onView(withId(R.id.title)).check(matches(isDisplayed()));
      //  onView(withId(R.id.description)).check(matches(isDisplayed()));
        onView(withId(R.id.created_by)).check(matches(isDisplayed()));
     //   onView(withId(R.id.created_by_title)).check(matches(isDisplayed()));
      //  onView(withId(R.id.source_title)).check(matches(isDisplayed()));
        onView(withId(R.id.source)).check(matches(isDisplayed()));

    }

}
