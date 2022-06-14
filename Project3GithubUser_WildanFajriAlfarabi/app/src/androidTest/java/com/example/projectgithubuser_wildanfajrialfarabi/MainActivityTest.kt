package com.example.projectgithubuser_wildanfajrialfarabi

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val dummySearchFound = "peppy"
    private val dummySearchNotFound = "aweiwoooo1123123"
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchNotFound() {
        onView(withId(R.id.search_view)).perform(typeText(dummySearchNotFound), closeSoftKeyboard())
        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.initial_text)).check(matches(isDisplayed()))
    }

    @Test
    fun searchFound() {
        onView(withId(R.id.search_view)).perform(typeText(dummySearchFound), closeSoftKeyboard())
        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.initial_text)).check(matches(isNotFocused()))
        onView(withId(R.id.github_userlist)).check(matches(isEnabled()))
    }

    @Test
    fun goToFav() {
        onView(withId(R.id.FavFAB)).perform(click())
        onView(withId(R.id.favActivity)).check(matches(isEnabled()))
    }
}