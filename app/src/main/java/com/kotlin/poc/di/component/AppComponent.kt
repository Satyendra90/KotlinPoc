package com.kotlin.poc.di.component

import com.kotlin.poc.NewsFeedApplication
import com.kotlin.poc.di.module.ApiModule
import com.kotlin.poc.webservice.ApiInterface
import dagger.Component
import javax.inject.Singleton

/**
 * dagger component that will look into api module for retrofit configuration
 */
@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {

    fun getApiInterface(): ApiInterface

    fun inject(application: NewsFeedApplication)
}