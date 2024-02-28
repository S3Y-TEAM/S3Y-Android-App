package com.graduation.core.base.network

import com.graduation.core.utils.logMe
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

fun <T : BaseResponse> globalNetworkCall(
    action: suspend () -> Response<T>,
    onReply: (suspend (ResponseState<T>) -> Unit)? = null,
    onToken: (suspend (String) -> Unit)? = null,
    doAfter: (suspend (isSuccess: Boolean) -> Unit)? = null,
    showLoading: Boolean = true,
    scope: CoroutineScope,
): Job {
    val tag = "globalNetworkCall"
    var isSuccess = false

    return scope.launch {
        if (showLoading) onReply?.invoke(ResponseState.Loading())
        try {
            val response = action()
            logMe(tag, response.body().toString())
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) onReply?.invoke(
                    ResponseState.Error(message = "Null body", errors = null)
                )
                else {
                    isSuccess = true
                    val token = response.headers()["authorization"]!!.split(" ")[1]
                    onToken!!.invoke(token)
                    onReply?.invoke(ResponseState.Success(data = body , token = token))
                }
            } else {
                val errorBody = getBodyError(response)
                val errorMsg = errorBody?.message

                when (response.code()) {
                    401 -> onReply?.invoke(ResponseState.NotAuthorized())
                    405 -> logMe(
                        tag, "Method Not Allowed : check API call method [POST/GET/..]"
                    )
                    500 -> onReply?.invoke(ResponseState.UnKnownError())

                    else -> {
                        onReply?.invoke(
                            ResponseState.Error(
                                message = errorMsg,
                                errors = errorBody?.errors,
                                data = response.body(),
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            logMe(tag, "networkCall: ${e.cause}\n.")
            logMe(tag, "networkCall: ${e.localizedMessage}")

            when (e) {
                is ConnectException -> {
                    onReply?.invoke(ResponseState.NetworkError())
                }

                is IOException -> {
                    if (e.localizedMessage?.contains("Unable to resolve host") == true)
                        onReply?.invoke(ResponseState.NetworkError())
                    else if (e.localizedMessage?.contains("timeout") == true)
                        onReply?.invoke(ResponseState.NetworkError())
                    else if (e.localizedMessage?.contains("Connection reset") == true)
                        onReply?.invoke(ResponseState.NetworkError())
                    else if (e.localizedMessage?.contains("java.net.SocketException: Socket closed") == true)
                        onReply?.invoke(ResponseState.NetworkError())
                    else onReply?.invoke(ResponseState.UnKnownError())
                }

                else -> onReply?.invoke(ResponseState.UnKnownError())
            }
        } finally {
            doAfter?.invoke(isSuccess)
        }

    }
}

fun <T> getBodyError(response: Response<T>): BaseResponse? {
    return try {
        val errorResponse =
            Gson().fromJson(response.errorBody()?.charStream(), BaseResponse::class.java)
        errorResponse
    } catch (exception: Exception) {
        logMe("getBodyError", exception.localizedMessage ?: "Unknown Error")
        null
    }
}
