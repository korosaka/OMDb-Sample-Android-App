package com.lyft.android.omdbsampleproject.repository

import android.util.Log
import com.lyft.android.omdbsampleproject.api.MovieAPIService
import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepositoryImpl : MovieRepositoryInterface {
    override fun fetchMoviesInfo(title: String, year: String?, page: Int): ResultMoviesInfo {//TODO nullable


        //TODO tyr catch
        val response = implementService()
            .fetchMoviesInfo(API_KEY, title, year, page)
            .execute()
        val resultEntity =
            response.body()

        val moviesData = mutableListOf<MovieData>()
        if (resultEntity == null || resultEntity!!.Search.isNullOrEmpty()) {
            return ResultMoviesInfo(listOf(), 0)
        }
        for (movieEntity in resultEntity!!.Search) {
            val movie = movieEntity.let {
                MovieData(it.imdbID, it.Title, it.Year, it.Poster, null)
            }
            moviesData.add(movie)
        }

        return ResultMoviesInfo(moviesData, resultEntity.totalResults.toInt())
    }

    private fun implementService(): MovieAPIService {
        return createRetrofit().create(MovieAPIService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val API_KEY = "1282a17f"
        const val BASE_API_URL = "https://www.omdbapi.com"
    }
}