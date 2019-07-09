package com.kotlin.poc.ui.activity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.kotlin.poc.R
import com.kotlin.poc.ui.adapter.NewsFeedAdapter
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun testRecyclerViewItem(){
        //if there are news feed item
        val rv = activityRule.activity.findViewById(R.id.rvNewsFeed) as RecyclerView
        val itemCount = rv.adapter?.itemCount

        onView(ViewMatchers.withId(R.id.rvNewsFeed))
                .perform(RecyclerViewActions.scrollToPosition<NewsFeedAdapter.ViewHolder>(itemCount!!.minus(1)))
    }

    @Test
    fun testNetworkError(){
        //to check network error
        onView(withText(R.string.no_internet_connection)).inRoot(withDecorView(not(activityRule.activity.window.decorView))).check(matches(isDisplayed()))
    }

    @Test
    fun testParsingError(){
        onView(withText(R.string.parsing_error)).inRoot(withDecorView(not(activityRule.activity.window.decorView))).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
    }
}