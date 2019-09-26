package com.example.trendingrepo.base.extensions


import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trendingrepo.R

fun AppCompatImageView.loadCircularImage(uri: String) =
    Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).placeholder(R.mipmap.ic_launcher).into(this)
