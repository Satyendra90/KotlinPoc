package com.kotlin.poc.webservice

import com.google.gson.annotations.SerializedName

data class NewsFeed(@SerializedName("title") val title: String?,
                    @SerializedName("description") val description: String?,
                    @SerializedName("imageHref") val image: String?)


data class NewsFeedResponse(@SerializedName("title") val title: String?,
                            @SerializedName("rows") val newsList: MutableList<NewsFeed>?)

data class ApiDataWrapper<T>(var data: T?, var isSuccess: Boolean, val error: ApiError?)

data class ApiError(val status: ErrorStatus, val msg: String)

enum class ErrorStatus{
    NETWORK_ERROR, PARSING_ERROR
}