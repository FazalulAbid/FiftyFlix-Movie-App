package com.fifty.fiftyflixmovies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.fiftyflixmovies.BuildConfig.API_KEY
import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.model.Movie
import retrofit2.HttpException
import java.io.IOException

class PopularMovieSource(private val api: TMDBService) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val popularMovies = api.getPopularMovies(API_KEY)
            LoadResult.Page(
                data = popularMovies.body()!!.movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (popularMovies.body()!!.movies.isEmpty()) null else 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}