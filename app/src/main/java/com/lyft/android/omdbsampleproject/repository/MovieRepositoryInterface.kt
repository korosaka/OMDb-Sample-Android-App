package com.lyft.android.omdbsampleproject.repository

import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo

interface MovieRepositoryInterface {
    fun fetchMoviesInfo(title: String, year: String?, page: Int = 1): ResultMoviesInfo?
}