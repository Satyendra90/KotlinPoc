package com.kotlin.poc.newsfeed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kotlin.poc.webservice.*
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

    private val compositeDisposable = CompositeDisposable()

    fun getNewsFeedListLiveData() : LiveData<ApiDataWrapper<NewsFeedResponse>>{
        return newsFeeds
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
        val disposable = newsFeedApi.getNewsFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { newFeedResponse ->
                    val itr = newFeedResponse.newsList?.iterator()

                    if(itr != null){
                        //filter data if its content is either null or blank
                        for (newsFeed in itr) {
                            if (newsFeed.title.isNullOrEmpty()
                                    && newsFeed.description.isNullOrEmpty()
                                    && newsFeed.image.isNullOrEmpty()) {
                                itr.remove()
                            }
                        }
                    }
                    newFeedResponse
                }
                .subscribeWith(object : ApiCallbackWrapper<NewsFeedResponse>(){
                    override fun onSuccess(t: NewsFeedResponse) {
                        newsFeeds.postValue(ApiDataWrapper(t, true, null))
                    }

                    override fun onError(error: ApiError) {
                        newsFeeds.postValue(ApiDataWrapper(null, false, error))
                    }
                })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
