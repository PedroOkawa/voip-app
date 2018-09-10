package com.okawa.voip.utils.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapUtils @Inject constructor() {

    companion object {
        private const val COMPACT_BITMAP_QUALITY = 50
        private const val COMPACT_BITMAP_WIDTH = 100
        private const val COMPACT_BITMAP_HEIGHT = 100
    }

    fun convertCompact(inputStream: InputStream?): ByteArray? {
        var data: ByteArray? = null
        try {
            val options = BitmapFactory.Options()
            options.inSampleSize = calculateInSampleSize(options, COMPACT_BITMAP_WIDTH, COMPACT_BITMAP_HEIGHT)
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPACT_BITMAP_QUALITY, byteArrayOutputStream)
            data = byteArrayOutputStream.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return data
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

}