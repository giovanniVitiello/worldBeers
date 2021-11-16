package com.example.worldbeers.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Resource<T>): Resource<T> = try {
    call.invoke()
} catch (e: Exception) {
    Resource.error(e.message.toString(), null)
}
