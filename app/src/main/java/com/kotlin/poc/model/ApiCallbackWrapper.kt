package com.kotlin.poc.model

import io.reactivex.observers.DisposableObserver
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * Callback that will process api result
 */
abstract class ApiCallbackWrapper<T>: DisposableObserver<T>() {

    protected abstract fun onSuccess(t: T)
    protected abstract fun onError(error : ApiError)

    override fun onNext(value: T) {
        onSuccess(value)
    }

    override fun onError(e: Throwable) {
        try {
            when (e) {
                is SocketException -> onError(ApiError(ErrorStatus.NETWORK_ERROR, e.message?: ""))
                is SocketTimeoutException -> onError(ApiError(ErrorStatus.NETWORK_ERROR, e.message?: ""))
                is IOException -> onError(ApiError(ErrorStatus.NETWORK_ERROR, e.message?: ""))
                else -> onError(ApiError(ErrorStatus.PARSING_ERROR, e.message?: ""))
            }
        } catch (ex: Exception) {
        }
    }

    override fun onComplete() {

    }
}