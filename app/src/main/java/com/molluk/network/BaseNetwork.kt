package com.molluk.network

import com.molluk.data.status.Resource
import retrofit2.Response

abstract class BaseNetwork {

    @Suppress("BlockingMethodInNonBlockingContext")
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                } else {
                    error("ERROR")
                }
            } else {
                error("ERROR")
            }
        } catch (e: Exception) {
            error("ERROR")
        }
    }

    private fun <T> error(errorMessage: String): Resource<T> {
        return Resource.error(errorMessage)
    }
}