package com.manoj.transformersae;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.base.IdlingResourceRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.view.View;

import com.manoj.transformersae.TestUtill.ViewVisibilityIdlingResource;
import com.manoj.transformersae.model.TestModel;
import com.manoj.transformersae.ui.MainActivity;
import com.manoj.transformersae.ui.list.FragmentList;
import com.manoj.transformersae.util.AppUtill;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
        mTestModel = TestModel.getInstance(testRule.getActivity());
        mTestModel.deleteAllTransformers();
    }

    @After
    public void cleanup() {
        AppUtill.setTesting(false);
    }

    @Test
    public void testItems() {
        mTestModel.loadUsers();
        View recycler_view = ((FragmentList)testRule.getActivity().getCurrentFragment()).getRootView();
        idlingResource = new ViewVisibilityIdlingResource(recycler_view, View.VISIBLE);

        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.bot_list)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(testRule.getActivity(), MainActivity.class);
        testRule.getActivity().startActivity(intent);
    }
}
