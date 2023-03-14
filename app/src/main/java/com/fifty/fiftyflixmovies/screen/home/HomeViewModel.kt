package com.fifty.fiftyflixmovies.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
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

//    private var _trendingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
//    val trendingMovies: State<Flow<PagingData<Movie>>> = _trendingMovies

    private var _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMovies: State<Flow<PagingData<Movie>>> = _popularMovies

    private var _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMovies: State<Flow<PagingData<Movie>>> = _upcomingMovies

    private var _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMovies: State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    private var _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Movie>>> = _topRatedMovies

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _trendingMovies.value = moviesRepository.getMovies()
            _bannerMovie.value = _trendingMovies.value?.random()
        }
    }
}