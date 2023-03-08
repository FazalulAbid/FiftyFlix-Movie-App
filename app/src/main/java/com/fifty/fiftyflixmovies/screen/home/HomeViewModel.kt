package com.fifty.fiftyflixmovies.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.fifty.fiftyflixmovies.data.repository.MoviesRepository
import com.fifty.fiftyflixmovies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
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
    private var _trendingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMovies: State<Flow<PagingData<Movie>>> = _trendingMovies

    init {
        getTrendingMovies(null)
    }

    // Banner Movie.
    fun fetchBannerMovie() {
        viewModelScope.launch {
            val movie = moviesRepository.getBannerMovie()
            _bannerMovie.value = movie
            Timber.tag("Hello").d("fetchBannerMovie: $movie")
        }
    }

    // Movies.
    private fun getTrendingMovies(genreId: Int?) {
        viewModelScope.launch {
            _trendingMovies.value = if (genreId != null) {
                moviesRepository.getTrendingMoviesThisWeek().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getTrendingMoviesThisWeek().cachedIn(viewModelScope)
            }
        }
    }
}