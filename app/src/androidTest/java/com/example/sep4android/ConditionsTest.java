package com.example.sep4android;

import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.sep4android.UI.Views.Activities.ConditionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ConditionsTest {
    @Rule
    public ActivityTestRule<ConditionActivity> activityTestRule =
            new ActivityTestRule<>(ConditionActivity.class);

    private static final int[] MENU_CONTENT_ITEMS_ID = {
            R.id.nav_home, R.id.nav_CO2, R.id.nav_humidity, R.id.nav_temperature
    };

    private Map<Integer, String> menuContent;

    private BottomNavigationView bottomNavigationView;

    @Before
    public void setUp() throws Exception {
        final ConditionActivity activity = activityTestRule.getActivity();
        bottomNavigationView = (BottomNavigationView) activity.findViewById(R.id.bottom_navigation);

        final Resources res = activity.getResources();
        menuContent = new HashMap<>(MENU_CONTENT_ITEMS_ID.length);
        menuContent.put(R.id.nav_home, res.getString(R.string.nav_home));
        menuContent.put(R.id.nav_CO2, res.getString(R.string.nav_co2));
        menuContent.put(R.id.nav_humidity, res.getString(R.string.nav_humidity));
        menuContent.put(R.id.nav_temperature, res.getString(R.string.nav_temperature));
    }

    @Test
    public void test_is_bottomnavigationview_visible() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
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
        for(int i = 1; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            onView(
                    allOf(
                            withId(MENU_CONTENT_ITEMS_ID[i]),
                            isDescendantOfA(withId(R.id.bottom_navigation)),
                            isDisplayed())).perform(click());
            assertTrue(bottomNavigationView.getMenu().findItem(MENU_CONTENT_ITEMS_ID[i]).isChecked());
            onView(withId(R.id.fragment_co2_container)).check(matches(isDisplayed()));
        }
        onView(allOf(withId(R.id.nav_home),isDescendantOfA(withId(R.id.bottom_navigation)),
                isDisplayed())).perform(click());
        onView(withId(R.id.fragment_home)).check(matches(isDisplayed()));
    }

    @Test
    public void test_buttons_visibility() {
        for(int i = 1; i < MENU_CONTENT_ITEMS_ID.length; i++) {
            onView (
                    allOf(
                            withId(MENU_CONTENT_ITEMS_ID[i]),
                            isDescendantOfA(withId(R.id.bottom_navigation)),
                            isDisplayed())).perform(click());
            onView(allOf(withId(R.id.startDateB), isDisplayed()));
            onView(allOf(withId(R.id.endDateB), isDisplayed()));
            onView(allOf(withId(R.id.resetB), isDisplayed()));
        }
    }

    @Test
    public void test_button_start_date_button() {
        onView (
                allOf(
                        withId(R.id.nav_CO2),
                        isDescendantOfA(withId(R.id.bottom_navigation)),
                        isDisplayed())).perform(click());
        onView(allOf(withId(R.id.startDateB), isDisplayed())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2017, 6,30));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void test_button_end_date_button() {
        onView (
                allOf(
                        withId(R.id.nav_CO2),
                        isDescendantOfA(withId(R.id.bottom_navigation)),
                        isDisplayed())).perform(click());
        onView(allOf(withId(R.id.startDateB), isDisplayed())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 6,30));
        onView(withText("OK")).perform(click());
    }
}
