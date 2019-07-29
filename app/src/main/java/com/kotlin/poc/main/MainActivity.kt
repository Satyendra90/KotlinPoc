package com.kotlin.poc.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.kotlin.poc.R
import com.kotlin.poc.newsfeed.NewsFeedFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Host activity for fragments
 */
class MainActivity : AppCompatActivity(), NewsFeedFragment.ToolBarTitleListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if(savedInstanceState == null){
            //show news fragment as default fragment
            addFragment(NewsFeedFragment.getInstance())
        }
    }

    override fun onToolbarTitleChange(title: String) {
        toolbar.title = title
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .add(R.id.flContentMain, fragment)
                .commit()
    }
}

