package com.azrinurvani.jetpackcomposeforbeginner.service

import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverManifestRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path

//TODO 7 - Create Interface for MarsRoverManifestService
interface MarsRoverManifestService {

    @GET("mars-photos/api/v1/manifests/{rover_name}?api_key=UgAlcmMlBdQwPZTNWz1X4eRYajYeFV16zgYTMBPm")
    suspend fun getMarsRoverManifest(
        @Path("rover_name") roverName : String
    ) : RoverManifestRemoteModel

//    companion object{
//        private const val BASE_URL = "https://api.nasa.gov/"
//
//        fun create() : MarsRoverManifestService{
//            val logger = HttpLoggingInterceptor()
////            logger.level = if (BuildConfig.DEBUG)  HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
//            logger.level = HttpLoggingInterceptor.Level.BODY
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .build()
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MarsRoverManifestService::class.java)
//        }
//    }

}