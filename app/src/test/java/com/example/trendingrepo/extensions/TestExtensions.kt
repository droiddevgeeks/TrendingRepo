package com.example.trendingrepo.extensions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

fun <T> T.toDeferred() = GlobalScope.async { this@toDeferred }