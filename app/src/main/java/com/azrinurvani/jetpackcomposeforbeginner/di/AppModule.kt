package com.azrinurvani.jetpackcomposeforbeginner.di

import android.util.Log
import com.azrinurvani.jetpackcomposeforbeginner.BuildConfig
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverManifestService
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverPhotoService
import com.azrinurvani.jetpackcomposeforbeginner.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//TODO 5 - Create AppModule
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesHttpLoggingInterceptor() : HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor{ message ->
            Log.d("API-LOG", message)
        }.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
//            level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    @Provides
    fun providesHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .callTimeout(Constants.CALL_TIME_OUT,TimeUnit.SECONDS)
            .connectTimeout(Constants.CONNECT_TIME_OUT,TimeUnit.SECONDS)
            .readTimeout(Constants.READ_TIME_OUT,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun providesRetrofitBuilder(httpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun providesMarsHoverManifestService(retrofit: Retrofit) : MarsRoverManifestService{
        return retrofit.create(MarsRoverManifestService::class.java)
    }

    //TODO 33 -  Add dependencies to provide MarsRoverPhotoService class
    @Provides
    fun providesMarsRoverPhotoService(retrofit: Retrofit) : MarsRoverPhotoService{
        return retrofit.create(MarsRoverPhotoService::class.java)
    }
}