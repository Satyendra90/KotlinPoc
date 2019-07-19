package com.kotlin.poc.ui.newsfeed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.kotlin.poc.R
import com.kotlin.poc.application.NewsFeedApplication
import com.kotlin.poc.factory.NewsFeedViewModelFactory
import com.kotlin.poc.model.*
import com.kotlin.poc.ui.base.BaseActivity
import com.kotlin.poc.webservice.NewsFeedApi
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Activity will show list of news feed
 */
class MainActivity : BaseActivity() {

    private lateinit var adapter: NewsFeedAdapter
    private lateinit var viewModel: NewsFeedViewModel

    @Inject lateinit var newsFeedApi: NewsFeedApi

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getIntentExtras(intent: Intent) {

    }

    override fun initEventAndData(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        NewsFeedApplication.getAppInstance().appComponent.inject(this)
        initializeRecyclerView()
        initializeViewModel()
        registerSwipeRefreshListener()
    }

    /**
     * configure recycler view and
     * set adapter with blank data
     */
    private fun initializeRecyclerView() {
        rvNewsFeed.setHasFixedSize(true)
        rvNewsFeed.layoutManager = LinearLayoutManager(this)

        adapter = NewsFeedAdapter(this)
        rvNewsFeed.adapter = adapter
    }

    /**
     * initialize the view model for news feed data and
     * register the observer
     */
    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, NewsFeedViewModelFactory(newsFeedApi)).get(NewsFeedViewModel::class.java)
        viewModel.getNewsFeedListLiveData().observe(this, Observer<ApiDataWrapper<NewsFeedResponse>> {

            if (it != null) {
                if (it.isSuccess) {
                    updateNewsFeedList(it.data)
                } else {
                    showNetworkErrorMessage(it.error)
                }
            }
        })

        viewModel.isLoadingLiveData().observe(this, Observer {
            if(it!!){
                showLoader()
            }
            else{
                hideLoader()
            }
        })
    }

    /**
     * register pull down listener
     */
    private fun registerSwipeRefreshListener(){
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshNewsFeed()
        }
    }

    /**
     * update list with new data
     */
    private fun updateNewsFeedList(newsFeedResponse: NewsFeedResponse?) {

        if (newsFeedResponse != null) {
            setToolbarTitle(newsFeedResponse.title ?: getString(R.string.app_name))

            val list: ArrayList<NewsFeed>? = newsFeedResponse.newsList
            if(list != null){
                adapter.setData(list)
            }
        }
    }

    private fun setToolbarTitle(title: String){
        toolbar.title = title
    }

    /**
     * show network error message
     */
    private fun showNetworkErrorMessage(error: ApiError?) {
        if (error != null && error.status == ErrorStatus.NETWORK_ERROR) {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        } else if (error != null && error.status == ErrorStatus.PARSING_ERROR) {
            Toast.makeText(this, getString(R.string.parsing_error), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * show loading animation of swipe refresh layout
     */
    private fun showLoader() {
        swipeRefreshLayout.isRefreshing = true
    }

    /**
     * hide loading animation
     */
    private fun hideLoader() {
        swipeRefreshLayout.isRefreshing = false
    }
}

