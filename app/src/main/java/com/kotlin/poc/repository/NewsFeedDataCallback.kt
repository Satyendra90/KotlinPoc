package com.kotlin.poc.repository

import com.kotlin.poc.model.ApiDataWrapper
import com.kotlin.poc.model.NewsFeedResponse

/**
 * callback to notify the api result data
 */
interface NewsFeedDataCallback {

    fun onNewsFeedLoaded(data: ApiDataWrapper<NewsFeedResponse>)
}