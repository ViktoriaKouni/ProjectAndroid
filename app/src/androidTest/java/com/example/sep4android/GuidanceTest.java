package com.example.sep4android;

import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.sep4android.UI.Views.Activities.GuidanceActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class GuidanceTest {
    @Rule
    public ActivityTestRule<GuidanceActivity> activityTestRule =
            new ActivityTestRule<>(GuidanceActivity.class);

    private static final int[] MENU_CONTENT_ITEMS_ID = {
            R.id.guidance_nav_CO2, R.id.guidance_nav_humidity, R.id.guidance_nav_temperature
    };

    private Map<Integer, String> menuContent;

    private BottomNavigationView bottomNavigationView;

    @Before
    public void setUp() throws Exception {
        final GuidanceActivity activity = activityTestRule.getActivity();
        bottomNavigationView = (BottomNavigationView) activity.findViewById(R.id.guidance_bottom_navigation);

        final Resources res = activity.getResources();
        menuContent = new HashMap<>(MENU_CONTENT_ITEMS_ID.length);
        menuContent.put(R.id.guidance_nav_CO2, res.getString(R.string.nav_co2));
        menuContent.put(R.id.guidance_nav_humidity, res.getString(R.string.nav_humidity));
        menuContent.put(R.id.guidance_nav_temperature, res.getString(R.string.nav_temperature));
    }

    @Test
    public void test_is_bottomnavigationview_visible() {
        onView(withId(R.id.guidance_bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void test_bottomnavigationview() {
        final Menu menu = bottomNavigationView.getMenu();
        assertNotNull(menu);

        assertEquals(MENU_CONTENT_ITEMS_ID.length, menu.size());

        for (int i = 0; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            final MenuItem currentItem = menu.getItem(i);
            assertEquals(i, MENU_CONTENT_ITEMS_ID[i], currentItem.getItemId());
        }
    }

    @Test
    public void test_bottomnavigationview_click() {
        for(int i=0; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            onView(
                    allOf(
                            withText(menuContent.get(MENU_CONTENT_ITEMS_ID[i])),
                            isDescendantOfA(withId(R.id.guidance_bottom_navigation)),
                            isDisplayed())).perform(click());
            assertTrue(bottomNavigationView.getMenu().findItem(MENU_CONTENT_ITEMS_ID[i]).isChecked());
        }
    }

    @Test
    public void test_fab_is_visible() {
        for(int i=0; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            onView(
                    allOf(
                            withText(menuContent.get(MENU_CONTENT_ITEMS_ID[i])),
                            isDescendantOfA(withId(R.id.guidance_bottom_navigation)),
                            isDisplayed())).perform(click());
        onView(withId(R.id.guidance_fab)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void test_fab_click() {
        for (int i = 0; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            onView(
                    allOf(
                            withText(menuContent.get(MENU_CONTENT_ITEMS_ID[i])),
                            isDescendantOfA(withId(R.id.guidance_bottom_navigation)),
                            isDisplayed())).perform(click());
            onView(withId(R.id.guidance_fab)).perform(click());
            onView(withId(R.id.add_guidance_layout)).check(matches(isDisplayed()));
            onView(allOf(withText("Never mind"))).perform(click());
        }
    }

    @Test
    public void test_fab_add_guidance() {
        for (int i = 0; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            onView(
                    allOf(
                            withText(menuContent.get(MENU_CONTENT_ITEMS_ID[i])),
                            isDescendantOfA(withId(R.id.guidance_bottom_navigation)),
                            isDisplayed())).perform(click());
            onView(withId(R.id.guidance_fab)).perform(click());
            onView(withId(R.id.guidance_description_dialog)).perform(typeText("Guidanc"));
            closeSoftKeyboard();
            onView(allOf(withText("Add New Guidance"))).perform(click());
        }
    }

    @Test
    public void test_rv_is_visible() {
        /*onView (
                allOf(
                        withText(menuContent.get(R.id.nav_CO2)),
                        isDescendantOfA(withId(R.id.guidance_bottom_navigation)),
                        isDisplayed())).perform(click());*/
        onView(withId(R.id.guidance_list_co2)).check(matches(isDisplayed()));
    }

    @Test
    public void test_rv_scroll() {
        RecyclerView recyclerView = activityTestRule.getActivity().findViewById(R.id.guidance_list_co2);
        int itemcount = recyclerView.getAdapter().getItemCount();

        onView(withId(R.id.guidance_list_co2)).perform(RecyclerViewActions.scrollToPosition(itemcount-1));
    }

    @Test
    public void test_rv_click() {
        onView(withId(R.id.guidance_fab)).perform(click());
        onView(withId(R.id.guidance_description_dialog)).perform(typeText("Guidanc"));
        closeSoftKeyboard();
        onView(allOf(withText("Add New Guidance"))).perform(click());

        RecyclerView recyclerView = activityTestRule.getActivity().findViewById(R.id.guidance_list_co2);
        int itemcount = recyclerView.getAdapter().getItemCount();

        onView(withId(R.id.guidance_list_co2)).perform(RecyclerViewActions.actionOnItemAtPosition(itemcount-1, click()));
    }

    @Test
    public void test_rv_edit() {
        onView(withId(R.id.guidance_fab)).perform(click());
        onView(withId(R.id.guidance_description_dialog)).perform(typeText("Guidanc"));
        closeSoftKeyboard();
        onView(allOf(withText("Add New Guidance"))).perform(click());

        RecyclerView recyclerView = activityTestRule.getActivity().findViewById(R.id.guidance_list_co2);
        int itemcount = recyclerView.getAdapter().getItemCount();

        onView(withId(R.id.guidance_list_co2)).perform(RecyclerViewActions.actionOnItemAtPosition(itemcount-1, click()));

        onView(withId(R.id.guidance_description_dialog)).perform(typeText("Guidance"));
        closeSoftKeyboard();
        onView(allOf(withText("Edit Guidance"))).perform(click());
    }
}
