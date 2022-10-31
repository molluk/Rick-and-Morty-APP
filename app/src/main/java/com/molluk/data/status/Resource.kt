package com.molluk.data.status

data class Resource<out T>(val status: Status, val response: T?, val error: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(response: T): Resource<T> {
            return Resource(Status.SUCCESS, response, null)
        }

        fun <T> error(message: String = "ERROR", response: T? = null): Resource<T> {
            return Resource(Status.ERROR, response, message)
        }

        fun <T> loading(response: T? = null): Resource<T> {
            return Resource(Status.LOADING, response, null)
        }
    }

}