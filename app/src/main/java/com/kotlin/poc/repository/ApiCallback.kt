package com.kotlin.poc.repository

import com.kotlin.poc.model.ApiError
import com.kotlin.poc.model.ErrorStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Callback that will process api result
 */
abstract class ApiCallback<T> : Callback<T>{

    protected abstract fun onSuccess(t: Response<T>?)
    protected abstract fun onError(error : ApiError)

    override fun onResponse(call: Call<T>?, t: Response<T>?) {
        onSuccess(t)
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        t?.printStackTrace()
        try {
            if(t is SocketTimeoutException) {
                onError(ApiError(ErrorStatus.NETWORK_ERROR, ""))
            } else if (t is IOException) {
                onError(ApiError(ErrorStatus.NETWORK_ERROR, ""))
            } else {
                onError(ApiError(ErrorStatus.PARSING_ERROR, ""))
            }
        } catch (ex: Exception) {
        }
    }
}