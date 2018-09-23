package com.manoj.transformersae.tests;

import android.content.Intent;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityItemsTest {
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
    public void testSingleTransformerHubcap() {
        mTestModel.loadMockDataSingleTransformer("HubcapTransformer.json", testRule.getActivity());
        View recycler_view = testRule.getActivity().findViewById(R.id.bot_list);
        idlingResource = new ViewVisibilityIdlingResource(recycler_view, View.VISIBLE);

        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("HubcapTest"))));
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void testTransformers() {
        mTestModel.loadMockDataTransformers("Transformers.json", testRule.getActivity());
        View recycler_view = testRule.getActivity().findViewById(R.id.bot_list);
        idlingResource = new ViewVisibilityIdlingResource(recycler_view, View.VISIBLE);

        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("HubcapTest"))));
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("SoundwaveTest"))));
        onView(withId(R.id.bot_list)).check(matches(hasDescendant(withText("BluestreakTest"))));
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
