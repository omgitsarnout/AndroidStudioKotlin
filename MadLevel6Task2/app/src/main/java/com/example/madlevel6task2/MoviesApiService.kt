package com.example.madlevel6task2

import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("/3/discover/movie?api_key=b0e30564015f60dab81691c53182f7d5&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    suspend fun getMostPopularMovies(@Query("primary_release_year") year: String): Movie
}