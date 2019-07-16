package com.kotlin.poc.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kotlin.poc.NewsFeedApplication
import com.kotlin.poc.model.ApiDataWrapper
import com.kotlin.poc.model.NewsFeedResponse
import com.kotlin.poc.repository.NewsFeedDataCallback
import com.kotlin.poc.repository.NewsFeedRepository
import com.kotlin.poc.webservice.ApiInterface

/**
 * view model to provide the news feed related data
 */
class NewsFeedViewModel : ViewModel() {

    private val apiInterface: ApiInterface = NewsFeedApplication.getAppInstance().getApiInterface()
    private var newsFeedRepository: NewsFeedRepository = NewsFeedRepository(apiInterface)

    private val newsFeeds : MutableLiveData<ApiDataWrapper<NewsFeedResponse>> by lazy {
        MutableLiveData<ApiDataWrapper<NewsFeedResponse>>().also {
            loadNewsFeed()
        }
    }

    fun getNewsFeedList() : LiveData<ApiDataWrapper<NewsFeedResponse>>{
        return newsFeeds
    }

    private val loadingLiveData = MutableLiveData<Boolean>()

    fun getLoadingLiveData(): LiveData<Boolean>{
        return loadingLiveData
    }

    /**
     * reload the data from repository
     */
    fun refreshNewsFeed(){
        loadNewsFeed()
    }

    /**
     * will get the news feed data from repository
     */
    private fun loadNewsFeed(){
        setLoadingVisibility(true)
        newsFeedRepository.getNewsFeed(object: NewsFeedDataCallback {
            override fun onNewsFeedLoaded(data: ApiDataWrapper<NewsFeedResponse>) {
                newsFeeds.postValue(data)
                setLoadingVisibility(false)
            }
        })
    }

    private fun setLoadingVisibility(visible: Boolean){
        loadingLiveData.postValue(visible)
    }
}