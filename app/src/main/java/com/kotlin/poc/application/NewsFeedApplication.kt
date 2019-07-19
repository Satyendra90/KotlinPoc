package com.kotlin.poc.application

import android.app.Application
import com.kotlin.poc.di.component.AppComponent
import com.kotlin.poc.di.component.DaggerAppComponent
import com.kotlin.poc.di.module.ApiModule
import com.kotlin.poc.webservice.NewsFeedApi

class NewsFeedApplication : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        private lateinit var instance: NewsFeedApplication

        fun getAppInstance(): NewsFeedApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().apiModule(ApiModule()).build()
    }
}