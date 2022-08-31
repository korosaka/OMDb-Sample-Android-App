package com.lyft.android.omdbsampleproject.api

import com.lyft.android.omdbsampleproject.api.entity.SearchResultEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPIService {
    @GET("/")
    fun fetchMoviesInfo(
        @Query("apikey") apiKey: String,
        @Query("s") title: String,
        @Query("y") year: String?,
        @Query("page") page: Int
    ): Call<SearchResultEntity>
}