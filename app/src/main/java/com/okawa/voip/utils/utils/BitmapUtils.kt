package com.okawa.voip.utils.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapUtils @Inject constructor() {

    fun convert(inputStream: InputStream?): Bitmap? {
        val bufferedInputStream = BufferedInputStream(inputStream)
        return BitmapFactory.decodeStream(bufferedInputStream)
    }

    fun convert(data: ByteArray?) = if(data!= null) BitmapFactory.decodeByteArray(data, 0, data.size) else null

    fun convert(bitmap: Bitmap?): ByteArray? {
        return if(bitmap!= null) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        } else {
            null
        }
    }

}