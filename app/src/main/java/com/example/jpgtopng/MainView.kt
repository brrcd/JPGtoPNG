package com.example.jpgtopng

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.SingleState
import java.io.File


interface MainView: MvpView {
    @AddToEndSingle
    fun showImage(imgFile: File)
    @AddToEndSingle
    fun onError(t: Throwable)
}