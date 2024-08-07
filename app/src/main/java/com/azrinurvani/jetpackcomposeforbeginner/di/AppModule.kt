package com.azrinurvani.jetpackcomposeforbeginner.di

import android.content.Context
import android.util.Log
import com.azrinurvani.jetpackcomposeforbeginner.BuildConfig
import com.azrinurvani.jetpackcomposeforbeginner.db.MarsRoverSavedDatabase
import com.azrinurvani.jetpackcomposeforbeginner.db.MarsRoverSavedPhotoDao
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverManifestService
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverPhotoService
import com.azrinurvani.jetpackcomposeforbeginner.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

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

    //TODO 50 - Add dependencies to provide mars rover saved dao
    @Provides
    fun provideMarsRoverSavedPhotoDao(@ApplicationContext context:Context) : MarsRoverSavedPhotoDao {
        return MarsRoverSavedDatabase.getInstance(context).marsRoverSavedPhotoDao()
    }

    //TODO 88 - Provide IoDispatcher using annotation class IoDispatcher
    @IoDispatcher
    @Provides
    fun provideIoDispatcher() : CoroutineDispatcher = Dispatchers.IO

}

//TODO 88 - Create annotation class for IoDispatcher
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher