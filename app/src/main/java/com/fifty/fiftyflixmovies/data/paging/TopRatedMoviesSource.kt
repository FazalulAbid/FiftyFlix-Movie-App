package com.fifty.fiftyflixmovies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fifty.fiftyflixmovies.data.remote.TMDBApi
import com.fifty.fiftyflixmovies.model.Movie
import retrofit2.HttpException
import java.io.IOException

class TopRatedMoviesSource(private val api: TMDBApi) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val topRatedMoviesList = api.getTopRatedMovies(nextPage)
            LoadResult.Page(
                data = topRatedMoviesList.searches,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (topRatedMoviesList.searches.isEmpty()) null else topRatedMoviesList.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }    }
}