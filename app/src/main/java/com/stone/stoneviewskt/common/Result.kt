package com.stone.stoneviewskt.common

sealed class Result<out T : Any?> {

    data class Success<out T : Any?>(val data: T) : Result<T?>()
    data class Failure<out T : Any?>(val data: T?) : Result<T?>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure<*> -> "Failure[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

fun <T : Any?> Result<T>.onSuccess(block: ((result: T) -> Unit)? = null): Result<T> {
    if (this is Result.Success<*>) {
        block?.invoke(data as T)
    }
    return this
}

fun <T : Any?> Result<T>.onFailure(block: ((result: T) -> Unit)? = null): Result<T> {
    if (this is Result.Failure<*>) {
        block?.invoke(data as T)
    }
    return this
}

fun <T: Any?> Result<T>.onError(block: (Exception) -> Unit): Result<T> {
    if (this is Result.Error) {
        block(exception)
    }
    return this
}
