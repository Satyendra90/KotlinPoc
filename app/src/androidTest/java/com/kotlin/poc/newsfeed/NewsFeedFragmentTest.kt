package com.kotlin.poc.newsfeed

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import com.kotlin.poc.R
import com.kotlin.poc.main.MainActivity
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsFeedFragmentTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    private lateinit var activity : MainActivity
    private lateinit var fragment : NewsFeedFragment

    @Before
    fun setUp() {
        fragment = NewsFeedFragment.getInstance()
        activity = activityRule.activity
    }

    @Test
    fun testLaunch(){
        val frameLayout = activity.findViewById(R.id.flContentMain) as FrameLayout
        assertNotNull(frameLayout)

        activity.supportFragmentManager.beginTransaction().add(frameLayout.id, fragment).commitAllowingStateLoss()

        getInstrumentation().waitForIdleSync()

        val view = fragment.view?.findViewById(R.id.rvNewsFeed) as RecyclerView
        assertNotNull(view)
    }

    @Test
    fun testRecyclerViewItem(){
        //if there are news feed item
        val rv = activity.findViewById(R.id.rvNewsFeed) as RecyclerView
        val itemCount = rv.adapter?.itemCount

        assertNotNull(rv)

        Espresso.onView(ViewMatchers.withId(R.id.rvNewsFeed))
                .perform(RecyclerViewActions.scrollToPosition<NewsFeedAdapter.ViewHolder>(itemCount!!.minus(1)))
    }

    @After
    fun tearDown() {

    }
}