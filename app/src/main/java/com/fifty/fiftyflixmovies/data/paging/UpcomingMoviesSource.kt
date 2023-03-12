package com.fifty.fiftyflixmovies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.fiftyflixmovies.BuildConfig.API_KEY
import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.model.Movie
import retrofit2.HttpException
import java.io.IOException

class UpcomingMoviesSource(private val api: TMDBService) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val upcomingMoviesList = api.getUpcomingMovies(nextPage, API_KEY)
            LoadResult.Page(
                data = upcomingMoviesList.searches,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (upcomingMoviesList.searches.isEmpty()) null else upcomingMoviesList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}