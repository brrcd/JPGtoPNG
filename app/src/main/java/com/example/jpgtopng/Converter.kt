package com.example.jpgtopng

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class Converter {

    fun convert(file: File) {
        val path = file.path
        val tempFile = File.createTempFile("new${file.name}", "png")
        val fos = FileOutputStream(tempFile)
        val bitmap = BitmapFactory.decodeFile(path.toString())
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
    }
}
