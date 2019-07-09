package com.kotlin.poc.repository

import com.kotlin.poc.model.ApiDataWrapper
import com.kotlin.poc.model.ApiError
import com.kotlin.poc.model.NewsFeedResponse
import com.kotlin.poc.repository.ApiCallback
import com.kotlin.poc.webservice.ApiInterface
import retrofit2.Response

/**
 * Repository that will act as single source for news feed
 * it will concat the remote and local db data
 * (Local db is not used for now)
 */
class NewsFeedRepository constructor(private val apiInterface : ApiInterface){

    /**
     * will get the news feed from remote
     */
    fun getNewsFeed(newsFeedCallback: NewsFeedDataCallback){

        apiInterface.getNewsFeed().enqueue(object : ApiCallback<NewsFeedResponse>() {

            override fun onSuccess(t: Response<NewsFeedResponse>?) {
                newsFeedCallback.onNewsFeedLoaded(ApiDataWrapper(t?.body(), true,null))
            }

            override fun onError(error: ApiError) {
                newsFeedCallback.onNewsFeedLoaded(ApiDataWrapper(null, false, error))
            }
        })
    }
}