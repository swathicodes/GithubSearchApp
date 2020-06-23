package com.swathi.githubapp;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.GrantPermissionRule;


import com.swathi.githubapp.models.UserDetails;
import com.swathi.githubapp.models.UserRepos;
import com.swathi.githubapp.models.UserReposItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private String githubUserId = "octocat";
    private MainActivity mainActivity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_NETWORK_STATE);

    /*
    Initialise user data before test
     */
    @Before
    public void initData(){
        mainActivity = activityRule.getActivity();
    }


    /*
    Tests whether clicking on a list item opens the Details Dialog
     */
    @Test
    public void testUserDetailsClick() throws InterruptedException {

        // Make sure that we are connected to internet
        Assert.assertTrue(isConnected(mainActivity));

        onView(withId(R.id.useridSearchET)).perform(ViewActions.typeText(githubUserId));
        onView(withId(R.id.searchBtn)).perform(click());

        // Idling Resource could be good replacement
        Thread.sleep(4000);

        UserDetails userDetails = mainActivity.mainActivityViewModel.userDetailsLiveData.getValue();
        UserRepos userReposItems = mainActivity.mainActivityViewModel.userReposLiveData.getValue();

        // Check that user details are not null and match the sampleId
        assertNotNull("User Details must not be null", userDetails);
        assertEquals("Username must match the input", userDetails.getName(), "The Octocat");
        assertNotNull("Avatar URL cannot be null",userDetails.getAvatar_url());
        assertNotEquals("Avatar URL cannot be empty", "", userDetails.getAvatar_url());

        // Check that user repos are non empty
        assertNotNull("User Repos must not be null",userReposItems);
        assertNotEquals("For this user we know there are some repos",0,userReposItems.size());

        //Click on UserRepo item and check that dialog is launched and correct details are sent
        onView(withId(R.id.reposListView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check that dialog is displayed. Because we know dialog contains "last updated" text
        onView(withText("Last Updated")).check(matches(isDisplayed()));

        // Check that details in the dialog match the clicked item in the list
        UserReposItem userRepo = userReposItems.get(0);
        onView(withId(R.id.last_updated)).check(matches(withText(userRepo.getUpdated_at())));
        onView(withId(R.id.tv_stars)).check(matches(withText(String.valueOf(userRepo.getStargazers_count()))));
        onView(withId(R.id.tv_forks)).check(matches(withText(String.valueOf(userRepo.getForks_count()))));

        // Check that back press closes the dialog
        Espresso.pressBack();
        onView(withText("Last Updated")).check(doesNotExist());

    }

    @Test
    public void testEmptySearch(){
        onView(withId(R.id.useridSearchET)).perform(ViewActions.typeText(""));
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withText(R.string.invalid_user)).inRoot(withDecorView(not(mainActivity.getWindow().getDecorView()))).check(matches(isDisplayed()));
    }


    // Util for checking internet connection
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
