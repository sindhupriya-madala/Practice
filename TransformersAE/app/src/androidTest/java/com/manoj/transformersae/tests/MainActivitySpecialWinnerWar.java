package com.manoj.transformersae.tests;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.manoj.transformersae.R;
import com.manoj.transformersae.TestUtill.ViewVisibilityIdlingResource;
import com.manoj.transformersae.model.TestModel;
import com.manoj.transformersae.ui.MainActivity;
import com.manoj.transformersae.util.AppUtill;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Manoj Vemuru on 2018-09-23.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivitySpecialWinnerWar {

    private TestModel mTestModel;
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            AppUtill.setTesting(true);
        }
    };

    @Before
    public void setup() {
        AppUtill.setTesting(true);
        mTestModel = TestModel.getInstance(testRule.getActivity());
        mTestModel.deleteAllTransformers();
    }

    @After
    public void cleanup() {
        AppUtill.setTesting(false);
        mTestModel.deleteAllTransformers();
    }

    @Test
    public void testWar() {
        mTestModel.loadMockDataTransformers("SpecialWinnerTransformer.json", testRule.getActivity());
        View recycler_view = testRule.getActivity().findViewById(R.id.bot_list);
        idlingResource = new ViewVisibilityIdlingResource(recycler_view, View.VISIBLE);

        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("HubcapTest"))));
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("Optimus Prime"))));
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("BluestreakTest"))));
        IdlingRegistry.getInstance().unregister(idlingResource);

        String expectedText = "Winning Team (Decipticons)";
        onView(withId(R.id.war_button)).perform(click());

        TextView dialog_view = testRule.getActivity().getDialogView().findViewById(R.id.title);
        idlingResource = new ViewVisibilityIdlingResource(dialog_view, View.VISIBLE);

        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.content)).check(matches(withText(expectedText)));
        IdlingRegistry.getInstance().unregister(idlingResource);

    }
}
