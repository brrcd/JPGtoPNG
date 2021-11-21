package com.example.jpgtopng

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.jpgtopng.App.Companion.appContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class MainPresenter(
    private val imageRepo: ImageRepo
) : MvpPresenter<MainView>() {

    private val disposable = CompositeDisposable()
    private val converter = Converter()
    private lateinit var imageFile: File

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        disposable +=
            imageRepo
                .getImage()
                // из полученного названия создаю файл
                // добавляю его в переменную для дальнейшей работы
                .map { img -> createFileFromName(img).also { imageFile = it } }
                .subscribe(
                    viewState::showImage,
                    viewState::onError
                )
    }

    // Здесь я создаю файл из полученого названия в папке res/raw
    private fun createFileFromName(fileName: String): File {
        val id = appContext.resources.getIdentifier(fileName, "raw", appContext.packageName)
        val ins = appContext.resources.openRawResource(id)
        val tempFile = File.createTempFile(fileName, "jpg")
        tempFile.deleteOnExit()
        val fos = FileOutputStream(tempFile)
        ins.copyTo(fos)
        return tempFile.also {
            fos.close()
        }
    }

    fun cancelConverting() {
        disposable.clear()
    }

    // отсчет 5 секунд для возможности отмены конвертации
    // проверял отмену с помощью вывода строки, всё работало
    // при нажатии на отмену, строка не выводилась
    fun startConverting() {
        disposable +=
            Single.just(imageFile)
                .delay(5, TimeUnit.SECONDS)
                .map { img -> converter.convert(img) }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}