package com.kotlin.poc.main

import android.app.Application
import com.kotlin.poc.dagger.component.AppComponent
import com.kotlin.poc.dagger.component.DaggerAppComponent
import com.kotlin.poc.dagger.module.ApiModule

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