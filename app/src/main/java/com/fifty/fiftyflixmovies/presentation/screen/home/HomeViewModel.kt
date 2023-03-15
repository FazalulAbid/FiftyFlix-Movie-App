package com.fifty.fiftyflixmovies.presentation.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.domain.repository.MovieRepository
import com.fifty.fiftyflixmovies.util.Constants.MOVIE_CACHE_LIFETIME_IN_MINUTES
import com.fifty.fiftyflixmovies.util.Constants.NOW_PLAYING_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.POPULAR_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.TOP_RATED_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.TRENDING_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.UPCOMING_MOVIES_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MovieRepository
) : ViewModel() {

    private val _selectedOption = mutableStateOf("Movies")
    val selectedOption: State<String> = _selectedOption

    private val _selectedGenre = mutableStateOf("")
    val selectedGenre: State<String> = _selectedGenre

    fun setSelectedOption(selectedOption: String) {
        _selectedOption.value = selectedOption
    }

    fun setGenre(genre: String) {
        _selectedGenre.value = genre
    }

    // Banner movie.
    private val _bannerMovie = MutableStateFlow<Movie?>(null)
    val bannerMovie: StateFlow<Movie?> = _bannerMovie

    // Movies states.
    private val _trendingMovies = MutableLiveData<List<Movie>>()
    val trendingMovies: MutableLiveData<List<Movie>> = _trendingMovies

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: MutableLiveData<List<Movie>> = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: MutableLiveData<List<Movie>> = _upcomingMovies

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: MutableLiveData<List<Movie>> = _nowPlayingMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: MutableLiveData<List<Movie>> = _topRatedMovies

    // Movie Detail.
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: MutableLiveData<Movie> = _selectedMovie


    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            val lastFetchTime = moviesRepository.getLastFetchedTime()
            val currentTime = System.currentTimeMillis()
            if (!(lastFetchTime != null &&
                        currentTime - lastFetchTime <= (MOVIE_CACHE_LIFETIME_IN_MINUTES * 60 * 1000))
            ) {
                moviesRepository.clearAllMoviesFromDB()
            }
            _trendingMovies.value = moviesRepository.getMoviesOfCategory(TRENDING_MOVIES_ID)
            _bannerMovie.value = _trendingMovies.value?.random()
            _popularMovies.value = moviesRepository.getMoviesOfCategory(POPULAR_MOVIES_ID)
            _upcomingMovies.value = moviesRepository.getMoviesOfCategory(UPCOMING_MOVIES_ID)
            _nowPlayingMovies.value = moviesRepository.getMoviesOfCategory(NOW_PLAYING_MOVIES_ID)
            _topRatedMovies.value = moviesRepository.getMoviesOfCategory(TOP_RATED_MOVIES_ID)
        }
    }

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _selectedMovie.value = moviesRepository.getMovie(movieId)
        }
    }

}