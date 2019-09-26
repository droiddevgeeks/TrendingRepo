package com.example.trendingrepo.base.model

import com.squareup.moshi.Json


data class ServerResponse<T>(
    @Json(name = "data") val data: T,
    @Json(name = "errors") val error: ServerError
)

data class ServerError(
    @Json(name = "status") val status: Int,
    @Json(name = "code") val code: Int
)

