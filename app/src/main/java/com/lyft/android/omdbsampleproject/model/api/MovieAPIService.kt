package com.lyft.android.omdbsampleproject.model.api

import com.lyft.android.omdbsampleproject.model.api.entity.MoviePlotEntity
import com.lyft.android.omdbsampleproject.model.api.entity.SearchResultEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {
    /**
     * Reference: http://www.omdbapi.com/
     */

    @GET("/")
    fun fetchMoviesInfo(
        @Query("apikey") apiKey: String,
        @Query("s") title: String,
        @Query("y") year: String?,
        @Query("page") page: Int
    ): Call<SearchResultEntity>

    @GET("/")
    fun fetchMoviePlot(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): Call<MoviePlotEntity>
}