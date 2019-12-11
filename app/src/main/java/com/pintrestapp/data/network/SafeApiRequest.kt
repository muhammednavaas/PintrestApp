package com.pintrestapp.data.network

import com.pintrestapp.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(Call: suspend () -> Response<T>): T {
        val response = Call.invoke()
        if (response.isSuccessful) {
            return response.body()!!

        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    message.append("\n")
                }
                message.append("Error Code : ${response.code()}")
            }
            throw ApiException(message.toString())
        }

    }

}