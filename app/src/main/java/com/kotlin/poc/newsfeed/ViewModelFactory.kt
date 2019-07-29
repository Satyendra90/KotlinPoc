package com.kotlin.poc.newsfeed

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kotlin.poc.webservice.NewsFeedApi

/**
 * will provide the instance of news feed view model
 */
class ViewModelFactory (private val newsFeedApi: NewsFeedApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
            NewsFeedViewModel(this.newsFeedApi) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}