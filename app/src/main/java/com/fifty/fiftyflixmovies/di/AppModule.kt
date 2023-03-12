package com.fifty.fiftyflixmovies.di

import android.content.Context
import androidx.room.Room
import com.fifty.fiftyflixmovies.BuildConfig.API_KEY
import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.db.GenreDao
import com.fifty.fiftyflixmovies.data.db.MovieCategoryDao
import com.fifty.fiftyflixmovies.data.db.MovieDao
import com.fifty.fiftyflixmovies.data.db.TMDBDatabase
import com.fifty.fiftyflixmovies.data.repository.movie.MoviesRepositoryImpl
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieCacheDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieLocalDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieRemoteDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl.MovieCacheDataSourceImpl
import com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl.MovieLocalDataSourceImpl
import com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl.MovieRemoteDataSourceImpl
import com.fifty.fiftyflixmovies.domain.repository.MovieRepository
import com.fifty.fiftyflixmovies.screen.home.HomeViewModel
import com.fifty.fiftyflixmovies.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesTMDBApi(okHttpClient: OkHttpClient): TMDBService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TMDBService::class.java)
    }

    @Singleton
    @Provides
    fun providesTMDBDatabase(@ApplicationContext applicationContext: Context): TMDBDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TMDBDatabase::class.java,
            "tmdb_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesMovieDao(database: TMDBDatabase): MovieDao =
        database.movieDao()

    @Singleton
    @Provides
    fun providesGenreDao(database: TMDBDatabase): GenreDao =
        database.genreDao()

    @Singleton
    @Provides
    fun providesMovieCategoryDao(database: TMDBDatabase): MovieCategoryDao =
        database.movieCategoryDao()

    @Singleton
    @Provides
    fun providesMovieRemoteDataSource(tmdbService: TMDBService): MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(tmdbService, API_KEY)

    @Singleton
    @Provides
    fun providesMovieLocalDataSource(movieDao: MovieDao): MovieLocalDataSource =
        MovieLocalDataSourceImpl(movieDao)

    @Singleton
    @Provides
    fun providesMovieCacheDataSource(): MovieCacheDataSource =
        MovieCacheDataSourceImpl()

    @Singleton
    @Provides
    fun providesMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        movieCacheDataSource: MovieCacheDataSource
    ): MovieRepository = MoviesRepositoryImpl(
        movieRemoteDataSource,
        movieLocalDataSource,
        movieCacheDataSource
    )

    @Provides
    fun providesHomeViewModel(movieRepository: MovieRepository): HomeViewModel =
        HomeViewModel(movieRepository)
}