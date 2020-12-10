package com.example.madlevel6task2

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val moviesRepository = MoviesRepository()

    /**
     * This property points direct to the LiveData in the repository, that value
     * get's updated when user clicks FAB. This happens through the getTriviaNumber() in this class :)
     */
    val movies = moviesRepository.trivia

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from view for error showing
     * Encapsulation :)
     */
    val errorText: LiveData<String>
        get() = _errorText

    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                //the triviaRepository sets it's own livedata property
                //our own trivia LiveData property points to te one in that repository
                moviesRepository.getPopularMovies()
            } catch (error: MoviesRepository.MoviesGetError) {
                _errorText.value = error.message
                Log.e("Movies error", error.cause.toString())
            }
        }
    }
}