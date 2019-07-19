package com.kotlin.poc.ui.newsfeed

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.kotlin.poc.R
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

    @After
    fun tearDown() {
    }
}