package com.example.madlevel6task2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout

class MoviesRepository {
    private val moviesApiService: MoviesApiService = MoviesApi.createApi()

    private val _movies: MutableLiveData<Movie> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val trivia: LiveData<Movie>
        get() = _movies

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getPopularMovies(year: String)  {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                moviesApiService.getMostPopularMovies(year)
            }

            _movies.value = result
        } catch (error: Throwable) {
            throw MoviesGetError("Unable to get movies", error)
        }
    }

    class MoviesGetError(message: String, cause: Throwable) : Throwable(message, cause)

}