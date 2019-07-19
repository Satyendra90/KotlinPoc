package com.kotlin.poc.ui.newsfeed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kotlin.poc.model.ApiCallbackWrapper
import com.kotlin.poc.model.ApiDataWrapper
import com.kotlin.poc.model.ApiError
import com.kotlin.poc.model.NewsFeedResponse
import com.kotlin.poc.webservice.NewsFeedApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * view model to provide the news feed related data
 */
class NewsFeedViewModel(private val newsFeedApi: NewsFeedApi): ViewModel() {

    private val newsFeeds : MutableLiveData<ApiDataWrapper<NewsFeedResponse>> by lazy {
        MutableLiveData<ApiDataWrapper<NewsFeedResponse>>().also {
            loadNewsFeed()
        }
    }

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val compositeDisposable = CompositeDisposable()

    fun getNewsFeedListLiveData() : LiveData<ApiDataWrapper<NewsFeedResponse>>{
        return newsFeeds
    }

    fun isLoadingLiveData(): LiveData<Boolean>{
        return loadingLiveData
    }

    /**
     * reload the data from repository
     */
    fun refreshNewsFeed(){
        loadNewsFeed()
    }

    /**
     * will get the news feed data from remote api
     */
    fun loadNewsFeed(){
        setLoadingVisibility(true)
        val disposable = newsFeedApi.getNewsFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiCallbackWrapper<NewsFeedResponse>(){
                    override fun onSuccess(t: NewsFeedResponse) {
                        newsFeeds.postValue(ApiDataWrapper(t, true,null))
                    }

                    override fun onError(error: ApiError) {
                        newsFeeds.postValue(ApiDataWrapper(null, false, error))
                        setLoadingVisibility(false)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        setLoadingVisibility(false)
                    }
                })

        compositeDisposable.add(disposable)
    }

    private fun setLoadingVisibility(visible: Boolean){
        loadingLiveData.postValue(visible)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
