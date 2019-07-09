package com.kotlin.poc.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.poc.BuildConfig
import com.kotlin.poc.webservice.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Module that provide instance required for retrofit api call
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{
        val logInterceptor = HttpLoggingInterceptor()
        if(BuildConfig.enableLog){
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        else{
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logInterceptor)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideGson() : Gson{
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}