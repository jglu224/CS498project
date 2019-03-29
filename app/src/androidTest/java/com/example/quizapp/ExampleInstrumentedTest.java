package com.example.quizapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.widget.Spinner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> spinnerTestRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.quizapp", appContext.getPackageName());
    }

    @Test
    public void testCategorySpinnerMusic(){
        String music = "Music";
        onView(withId(R.id.spinner_category)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(music))).perform(click());
    }

    @Test
    public void testCategorySpinnerVG(){
        String vidGames = "Video Games";
        onView(withId(R.id.spinner_category)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vidGames))).perform(click());
    }

    @Test
    public void testDifficultySpinnerEasy(){
        String easy = "Easy";
        onView(withId(R.id.spinner_difficulty)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(easy))).perform(click());

    }

    @Test
    public void testDifficultySpinnerMedium(){
        String medium = "Medium";
        onView(withId(R.id.spinner_difficulty)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(medium))).perform(click());
    }

    @Test
    public void testDifficultySpinnerHard(){
        String hard = "Hard";
        onView(withId(R.id.spinner_difficulty)).perform(click());
        //onData(allOf(is(instanceOf(String.class)))).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(hard))).perform(click());
        //onView(withId(R.id.spinner_category)).check(matches(withSpinnerText(containsString(music))));
    }
}
