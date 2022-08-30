package com.lyft.android.omdbsampleproject.model

import android.graphics.Bitmap

data class MovieData(val id: String, val title: String, val year: String, val posterUrl: String, var poster: Bitmap?)
