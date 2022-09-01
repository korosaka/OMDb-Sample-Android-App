package com.lyft.android.omdbsampleproject.model.repository.movies_info

import com.lyft.android.omdbsampleproject.model.api.MovieAPIService
import com.lyft.android.omdbsampleproject.model.MovieData
import com.lyft.android.omdbsampleproject.model.ResultMoviesInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepositoryImpl : MovieRepositoryInterface {
    override fun fetchMoviesInfo(title: String, year: String?, page: Int): ResultMoviesInfo? {
        try {
            val response = implementService()
                .fetchMoviesInfo(API_KEY, title, year, page)
                .execute()
            val resultEntity =
                response.body()

            val movieArray = resultEntity?.Search
            val totalCount = resultEntity?.totalResults
            if (movieArray.isNullOrEmpty() || totalCount.isNullOrEmpty()) return createEmptyResult()

            val moviesData = mutableListOf<MovieData>()
            for (movieEntity in movieArray) {
                moviesData.add(movieEntity.let {
                    MovieData(it.imdbID, it.Title, it.Year, it.Poster, null)
                })
            }

            return ResultMoviesInfo(moviesData, totalCount.toInt())
        } catch (e: Exception) {
            return null
        }
    }

    override fun fetchMoviePlot(id: String): String? {
        return try {
            val response = implementService()
                .fetchMoviePlot(API_KEY, id)
                .execute()
            val plotEntity =
                response.body()
            plotEntity?.Plot
        } catch (e: Exception) {
            null
        }
    }

    private fun createEmptyResult() = ResultMoviesInfo(listOf(), 0)

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