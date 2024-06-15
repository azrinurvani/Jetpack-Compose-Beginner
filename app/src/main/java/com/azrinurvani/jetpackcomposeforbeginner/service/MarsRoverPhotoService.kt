package com.azrinurvani.jetpackcomposeforbeginner.service

import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverPhotoRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//TODO 32 - Create API Service MarsRoverPhotoService and function to get mars-photo
interface MarsRoverPhotoService {

    @GET("mars-photos/api/v1/rovers/{rover_name}/photos?api_key=UgAlcmMlBdQwPZTNWz1X4eRYajYeFV16zgYTMBPm")
    suspend fun getMarsRoverPhoto(
        @Path("rover_name") roverName : String,
        @Query("sol") sol : String
    ) : RoverPhotoRemoteModel
}