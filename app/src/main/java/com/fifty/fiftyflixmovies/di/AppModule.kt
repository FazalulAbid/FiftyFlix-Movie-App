package com.fifty.fiftyflixmovies.di

import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.db.GenreDao
import com.fifty.fiftyflixmovies.data.db.MovieDao
import com.fifty.fiftyflixmovies.data.db.TMDBDatabase
import com.fifty.fiftyflixmovies.data.repository.MoviesRepository
import com.fifty.fiftyflixmovies.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

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

    @Provides
    fun providesMovieDao(database: TMDBDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun providesGenreDao(database: TMDBDatabase): GenreDao {
        return database.genreDao()
    }

    @Singleton
    @Provides
    fun providesMoviesRepository(api: TMDBService) = MoviesRepository(api)
}