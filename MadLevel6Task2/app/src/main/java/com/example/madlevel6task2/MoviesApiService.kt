package com.example.madlevel6task2

import retrofit2.http.GET

interface MoviesApiService {
    @GET("/random/trivia?json")
    suspend fun getRandomNumberTrivia(): Trivia
}