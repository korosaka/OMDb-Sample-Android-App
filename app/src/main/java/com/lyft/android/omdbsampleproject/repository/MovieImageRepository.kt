package com.lyft.android.omdbsampleproject.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class MovieImageRepository {
    fun fetchImage(imageUrlStr: String): Bitmap {//TODO nullable
        //TODO try catch
        URL(imageUrlStr).openStream().use {
            return BitmapFactory.decodeStream(it)
        }
    }
}