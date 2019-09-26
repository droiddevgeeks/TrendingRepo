package com.example.trendingrepo.base.extensions


import android.content.Context
import android.widget.Toast

fun Context.showLongToast(str: String) {

    Toast.makeText(this, str, Toast.LENGTH_LONG).show()
}
fun Context.showShortToast(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}