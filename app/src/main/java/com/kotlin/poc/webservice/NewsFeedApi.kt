package com.kotlin.poc.webservice

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * it will have all the end point of web service
 */
interface NewsFeedApi {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getNewsFeed(): Observable<NewsFeedResponse>
}