package com.kotlin.poc.dagger.component

import com.kotlin.poc.dagger.module.ApiModule
import com.kotlin.poc.newsfeed.NewsFeedFragment
import com.kotlin.poc.webservice.NewsFeedApi
import dagger.Component
import javax.inject.Singleton

/**
 * dagger component that will look into api module for retrofit configuration
 */
@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {
    fun getNewsFeedApiService(): NewsFeedApi
    fun inject(fragment: NewsFeedFragment)
}