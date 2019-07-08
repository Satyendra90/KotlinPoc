package com.kotlin.poc.webservice

import com.kotlin.poc.model.NewsFeedResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * it will have all the end point of web service
 */
interface ApiInterface {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getNewsFeed(): Call<NewsFeedResponse>
}