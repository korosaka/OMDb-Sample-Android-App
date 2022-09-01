package com.lyft.android.omdbsampleproject.model.repository.movie_image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.lang.Exception
import java.net.URL

class MovieImageRepository {
    fun fetchImage(imageUrlStr: String): Bitmap? {
        try {
            URL(imageUrlStr).openStream().use {
                return BitmapFactory.decodeStream(it)
            }
        } catch (e: Exception) {
            return null
        }
    }
}