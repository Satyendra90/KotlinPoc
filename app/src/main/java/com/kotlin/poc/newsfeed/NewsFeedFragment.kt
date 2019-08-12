package com.kotlin.poc.newsfeed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlin.poc.R
import com.kotlin.poc.main.NewsFeedApplication
import com.kotlin.poc.webservice.*
import kotlinx.android.synthetic.main.fragment_news_feed.*
import javax.inject.Inject

/**
 * fragment will show list of news feed
 */
class NewsFeedFragment : Fragment(){

    private lateinit var adapter: NewsFeedAdapter
    private lateinit var viewModel: NewsFeedViewModel
    private var toolBarTitleListener: ToolBarTitleListener? = null

    @Inject
    lateinit var newsFeedApi: NewsFeedApi

    interface ToolBarTitleListener{
        fun onToolbarTitleChange(title: String)
    }

    companion object {
        fun getInstance(): NewsFeedFragment {
            return NewsFeedFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is ToolBarTitleListener){
            toolBarTitleListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewsFeedApplication.getAppInstance().appComponent.inject(this)
        initializeRecyclerView()
        registerSwipeRefreshListener()
        initializeViewModel()
    }

    /**
     * configure recycler view and
     * set adapter with blank data
     */
    private fun initializeRecyclerView() {
        rvNewsFeed.setHasFixedSize(true)
        rvNewsFeed.layoutManager = LinearLayoutManager(activity)

        adapter = NewsFeedAdapter(context!!)
        rvNewsFeed.adapter = adapter
    }

    /**
     * initialize the view model for news feed data and
     * register the observer
     */
    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(newsFeedApi)).get(NewsFeedViewModel::class.java)

        showLoader()
        viewModel.getNewsFeedListLiveData().observe(this, Observer<ApiDataWrapper<NewsFeedResponse>> {
            hideLoader()
            if (it != null) {
                if (it.isSuccess) {
                    updateNewsFeedList(it.data)
                } else {
                    showNetworkErrorMessage(it.error)
                }
            }
        })
    }

    /**
     * register pull down listener
     */
    private fun registerSwipeRefreshListener(){
        swipeRefreshLayout.setOnRefreshListener {
            showLoader()
            showNoDataFoundMessage(false)
            viewModel.refreshNewsFeed()
        }
    }

    /**
     * update list with new data
     */
    private fun updateNewsFeedList(newsFeedResponse: NewsFeedResponse?) {

        if (newsFeedResponse != null) {
            toolBarTitleListener?.onToolbarTitleChange(newsFeedResponse.title ?: getString(R.string.app_name))
            showNoDataFoundMessage(newsFeedResponse.newsList.isNullOrEmpty())

            val list: List<NewsFeed>? = newsFeedResponse.newsList
            if(list != null){
                adapter.setData(list)
            }
        }
    }

    /**
     * show network error message
     */
    private fun showNetworkErrorMessage(error: ApiError?) {
        if (error != null && error.status == ErrorStatus.NETWORK_ERROR) {
            Toast.makeText(activity, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        } else if (error != null && error.status == ErrorStatus.PARSING_ERROR) {
            Toast.makeText(activity, getString(R.string.parsing_error), Toast.LENGTH_SHORT).show()
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

    /**
     * show no data found message if list is either null or empty
     */
    private fun showNoDataFoundMessage(noDataFound: Boolean){
        if(noDataFound){
            tvNoDataFound.visibility = View.VISIBLE
        }
        else{
            tvNoDataFound.visibility = View.GONE
        }
    }
}