package com.kotlin.poc

import android.app.Application
import com.kotlin.poc.di.component.AppComponent
import com.kotlin.poc.di.component.DaggerAppComponent
import com.kotlin.poc.di.module.ApiModule
import com.kotlin.poc.webservice.ApiInterface

class NewsFeedApplication : Application() {

    private lateinit var appComponent: AppComponent

    companion object {
        private lateinit var instance: NewsFeedApplication

        fun getAppInstance(): NewsFeedApplication{
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().apiModule(ApiModule()).build()
        appComponent.inject(this)
    }

    /**
     * will provide the api interface object for api call
     */
    fun getApiInterface(): ApiInterface {
        return appComponent.getApiInterface()
    }
}