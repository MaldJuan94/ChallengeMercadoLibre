package com.mercadolibre.app.network

import com.mercadolibre.app.utils.CONNECT_EXCEPTION
import com.mercadolibre.app.utils.SOCKET_TIME_OUT_EXCEPTION
import com.mercadolibre.app.utils.UNKNOWN_HOST_EXCEPTION
import com.mercadolibre.app.utils.UNKNOWN_NETWORK_EXCEPTION
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
    try {
        val response = call.invoke()
        if (response.isSuccessful && response.body() != null) {
            return NetworkStatus.Success(response.body())
        }
        return NetworkStatus.Error(response.message())
    } catch (e: Exception) {
        e.printStackTrace()
        return when (e) {
            is ConnectException -> {
                NetworkStatus.Error(CONNECT_EXCEPTION)
            }
            is UnknownHostException -> {
                NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION)
            }
            is SocketTimeoutException -> {
                NetworkStatus.Error(SOCKET_TIME_OUT_EXCEPTION)
            }
            is HttpException -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
            else -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }
}