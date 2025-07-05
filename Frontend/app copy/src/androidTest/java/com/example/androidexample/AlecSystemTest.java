package com.example.androidexample;

import com.example.androidexample.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class AlecSystemTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void login(){
        // Click button to submit
        onView(withId(R.id.main_login_btn)).perform(click());
        String username = "aferchen";
        String pasword = "123$";

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        // Type in testString and send request
        onView(withId(R.id.login_username_edt)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password_edt)).perform(typeText(pasword), closeSoftKeyboard());

        onView(withId(R.id.login_login_btn)).perform(click());
        // check if the text is correct (same as in DashboardViewModel)
        ViewInteraction dashboard = onView(withText("Welcome back,")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void badUsername(){
        // Click button to submit
        onView(withId(R.id.main_login_btn)).perform(click());
        String username = "aaferchen";
        String pasword = "123$";

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        // Type in testString and send request
        onView(withId(R.id.login_username_edt)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password_edt)).perform(typeText(pasword), closeSoftKeyboard());

        onView(withId(R.id.login_login_btn)).perform(click());
        // check if the text is correct (same as in DashboardViewModel)
        ViewInteraction dashboard = onView(withText("Login to Cynance")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void navBar(){
        // Click button to submit
        onView(withId(R.id.main_login_btn)).perform(click());
        String username = "aferchen";
        String pasword = "123$";

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        // Type in testString and send request
        onView(withId(R.id.login_username_edt)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password_edt)).perform(typeText(pasword), closeSoftKeyboard());

        onView(withId(R.id.login_login_btn)).perform(click());
        onView(withId(R.id.goalsBarButton)).perform(click());
        // check if the text is correct (same as in DashboardViewModel)
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        onView(withId(R.id.financialAdvisorButton)).perform(click());
        ViewInteraction dashboard2 = onView(withText("Speak to a Financial Advisor")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void profileSignOut(){
        // Click button to submit
        onView(withId(R.id.main_login_btn)).perform(click());
        String username = "aferchen";
        String pasword = "123$";

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        // Type in testString and send request
        onView(withId(R.id.login_username_edt)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password_edt)).perform(typeText(pasword), closeSoftKeyboard());

        onView(withId(R.id.login_login_btn)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.signOutButton)).perform(click());
        // check if the text is correct (same as in DashboardViewModel)
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        ViewInteraction dashboard2 = onView(withId(R.id.logoID)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
