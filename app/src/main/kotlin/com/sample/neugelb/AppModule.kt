package com.sample.neugelb

import android.content.Context
import com.sample.base.NetworkConnectionInterceptor
import com.sample.neugelb.data.remote.MoviesApi
import com.sample.neugelb.data.repository.MoviesRepositoryImpl
import com.sample.neugelb.domain.usecase.GetNowPlayingUseCase
import com.sample.neugelb.domain.usecase.SearchMovieUseCase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideNetworkInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor =
        NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun provideClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val logger = object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) = Timber.d(message)
            }
            val interceptor = HttpLoggingInterceptor(logger).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            this.addInterceptor(interceptor)
            this.addInterceptor(networkConnectionInterceptor)
        }
    }.build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    internal fun provideRepository(api: MoviesApi): MoviesRepositoryImpl =
        MoviesRepositoryImpl(api)

    @Singleton
    @Provides
    internal fun provideApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)

    @Singleton
    @Provides
    internal fun provideGetNowPlayingUseCase(
        moviesRepository: MoviesRepositoryImpl
    ): GetNowPlayingUseCase =
        GetNowPlayingUseCase(moviesRepository)

    @Singleton
    @Provides
    internal fun provideSearchMovieUseCase(
        moviesRepository: MoviesRepositoryImpl
    ): SearchMovieUseCase =
        SearchMovieUseCase(moviesRepository)
}