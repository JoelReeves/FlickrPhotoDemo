package net.joelreeves.flickrphotodemo;

import android.support.test.rule.ActivityTestRule;

import net.joelreeves.flickrphotodemo.ui.activities.PhotosActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PhotosActivityTests {

    @Rule
    public ActivityTestRule<PhotosActivity> activityRule = new ActivityTestRule<>(PhotosActivity.class);

    @Test
    public void testToolbarShouldBeVisible() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testRecyclerViewShouldBeVisible() {
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void testRefreshButtonShouldBeClickable() {
        onView(withId(R.id.toolbar)).perform(click());
    }
}
