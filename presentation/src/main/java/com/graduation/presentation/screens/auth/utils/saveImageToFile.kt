package com.graduation.presentation.screens.auth.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveImageToFile(context: Context, bitmap: Bitmap): File? {
    val file = File(context.cacheDir, "image.jpg")
    return try {
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()

        file
    } catch (e: IOException) {
        e.printStackTrace()

        null
    }
}

fun imageRefactored(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val myBitmap = BitmapFactory.decodeStream(inputStream)

        val stream = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        inputStream!!.close()

        saveImageToFile(context, myBitmap)

    } catch (e: IOException) {
        e.printStackTrace()

        null
    }
}

object FileUtils {
    fun getPath(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
            cursor = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.let {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (it.moveToFirst()) {
                    val fileName = it.getString(nameIndex)
                    val file = File(context.cacheDir, fileName)
                    return if (file.exists()) {
                        file.delete()
                        null
                    } else {
                        file.absolutePath
                    }
                }
            }
            null
        } finally {
            cursor?.close()
        }
    }
}