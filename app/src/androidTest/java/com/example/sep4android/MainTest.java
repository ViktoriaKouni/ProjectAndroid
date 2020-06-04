package com.example.sep4android;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.sep4android.UI.Views.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.sep4android", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_is_recyclerview_visible() {
        onView(withId(R.id.rooms)).check(matches(isDisplayed()));
    }

    @Test
    public void test_scrolling_on_recyclerview_item() {
        RecyclerView recyclerView = mainActivityActivityTestRule.getActivity().findViewById(R.id.rooms);
        int itemcount = recyclerView.getAdapter().getItemCount();

       onView(withId(R.id.rooms)).perform(RecyclerViewActions.scrollToPosition(itemcount-1));
    }

    @Test
    public void test_recycleview_click() {
        RecyclerView recyclerView = mainActivityActivityTestRule.getActivity().findViewById(R.id.rooms);
        int itemcount = recyclerView.getAdapter().getItemCount();

       // onView(withId(R.id.rooms)).perform(RecyclerViewActions.actionOnItemAtPosition(itemcount-1, click()));
    }
}
