package com.example.jpgtopng

import io.reactivex.rxjava3.core.Single

class ImageRepo {

    private val imgName = "imjpg"

    fun getImage(): Single<String> = Single.just(imgName)
}