package com.fifty.fiftyflixmovies.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fifty.fiftyflixmovies.BuildConfig.API_KEY
import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.db.*
import com.fifty.fiftyflixmovies.data.repository.download.DownloadRepositoryImpl
import com.fifty.fiftyflixmovies.data.repository.download.datasource.ThumbnailLocalDataSource
import com.fifty.fiftyflixmovies.data.repository.download.datasourceimpl.ThumbnailLocalDataSourceImpl
import com.fifty.fiftyflixmovies.data.repository.movie.MoviesRepositoryImpl
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieCacheDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieLocalDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieRemoteDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl.MovieCacheDataSourceImpl
import com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl.MovieLocalDataSourceImpl
import com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl.MovieRemoteDataSourceImpl
import com.fifty.fiftyflixmovies.data.sharedpreference.SharedPreferenceHelper
import com.fifty.fiftyflixmovies.domain.repository.DownloadRepository
import com.fifty.fiftyflixmovies.domain.repository.MovieRepository
import com.fifty.fiftyflixmovies.presentation.screen.download.AndroidDownloader
import com.fifty.fiftyflixmovies.presentation.screen.download.DownloadViewModel
import com.fifty.fiftyflixmovies.presentation.screen.download.Downloader
import com.fifty.fiftyflixmovies.presentation.screen.home.HomeViewModel
import com.fifty.fiftyflixmovies.util.Constants.BASE_URL
import com.fifty.fiftyflixmovies.util.Constants.movieCategories
import com.fifty.fiftyflixmovies.util.FirebaseConstants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    @Volatile
    private var INSTANCE: TMDBDatabase? = null

    private class TBDBDatabaseCallback(
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    it.movieCategoryDao().insertMovieCategory(movieCategories)
                }
            }
        }
    }

    @Singleton
    @Provides
    fun providesTMDBDatabase(
        @ApplicationContext applicationContext: Context
    ): TMDBDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                applicationContext,
                TMDBDatabase::class.java,
                "tmdb_db"
            )
                .addCallback(TBDBDatabaseCallback())
                .build()
                .also { INSTANCE = it }
            instance
        }
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
    fun providesMovieLocalDataSource(
        movieDao: MovieDao,
        movieCategoryDao: MovieCategoryDao
    ): MovieLocalDataSource =
        MovieLocalDataSourceImpl(movieDao, movieCategoryDao)

    @Singleton
    @Provides
    fun providesMovieCacheDataSource(): MovieCacheDataSource =
        MovieCacheDataSourceImpl()

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceHelper(sharedPreferences: SharedPreferences): SharedPreferenceHelper =
        SharedPreferenceHelper(sharedPreferences)

    @Singleton
    @Provides
    fun providesMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
        movieCacheDataSource: MovieCacheDataSource,
        sharedPreferenceHelper: SharedPreferenceHelper
    ): MovieRepository = MoviesRepositoryImpl(
        movieRemoteDataSource,
        movieLocalDataSource,
        movieCacheDataSource,
        sharedPreferenceHelper
    )

    @Provides
    fun providesHomeViewModel(movieRepository: MovieRepository): HomeViewModel =
        HomeViewModel(movieRepository)

    @Singleton
    @Provides
    fun providesFirebaseStorage(): StorageReference =
        FirebaseStorage.getInstance().getReference(FirebaseConstants.ROOT_DIRECTORY)

    @Singleton
    @Provides
    fun providesThumbnailDao(database: TMDBDatabase): ThumbnailDao =
        database.thumbnailDao()

    @Singleton
    @Provides
    fun providesThumbnailLocalDataSource(
        thumbnailDao: ThumbnailDao
    ): ThumbnailLocalDataSource =
        ThumbnailLocalDataSourceImpl(thumbnailDao)

    @Singleton
    @Provides
    fun providesDownloadRepository(
        thumbnailLocalDataSource: ThumbnailLocalDataSource,
        storageReference: StorageReference
    ): DownloadRepository =
        DownloadRepositoryImpl(thumbnailLocalDataSource, storageReference)

    @Provides
    fun providesDownloader(@ApplicationContext context: Context): Downloader =
        AndroidDownloader(context)

    @Provides
    fun providesDownloadsViewModel(
        application: Application,
        downloadViewModel: DownloadRepository,
        downloader: Downloader
    ): DownloadViewModel =
        DownloadViewModel(application, downloadViewModel, downloader)

}