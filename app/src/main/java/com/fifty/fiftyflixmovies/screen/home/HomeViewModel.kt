package com.fifty.fiftyflixmovies.screen.home

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

    private var _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMovies: State<Flow<PagingData<Movie>>> = _popularMovies

    private var _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMovies: State<Flow<PagingData<Movie>>> = _upcomingMovies

    private var _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMovies: State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    private var _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies

    init {
        getTrendingMovies(null)
        getPopularMovies(null)
        getUpcomingMovies(null)
        nowPlayingMovies(null)
        topRatedMovies(null)
    }

    // Banner Movie.
    fun fetchBannerMovie() {
        viewModelScope.launch {
            val movie = moviesRepository.getBannerMovie()
            _bannerMovie.value = movie
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

    private fun getPopularMovies(genreId: Int?) {
        viewModelScope.launch {
            _popularMovies.value = if (genreId != null) {
                moviesRepository.getPopularMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getPopularMovies().cachedIn(viewModelScope)
            }
        }
    }

    private fun getUpcomingMovies(genreId: Int?) {
        viewModelScope.launch {
            _upcomingMovies.value = if (genreId != null) {
                moviesRepository.getUpcomingMovie().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getUpcomingMovie().cachedIn(viewModelScope)
            }
        }
    }

    private fun nowPlayingMovies(genreId: Int?) {
        viewModelScope.launch {
            _nowPlayingMovies.value = if (genreId != null) {
                moviesRepository.nowPlayingMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.nowPlayingMovies().cachedIn(viewModelScope)
            }
        }
    }

    private fun topRatedMovies(genreId: Int?) {
        viewModelScope.launch {
            _topRatedMovies.value = if (genreId != null) {
                moviesRepository.topRatedMovies().map { pagingData ->
                    pagingData.filter {
                        it.genreIds.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.topRatedMovies().cachedIn(viewModelScope)
            }
        }
    }
}