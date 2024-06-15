package com.azrinurvani.jetpackcomposeforbeginner.service.model

import com.google.gson.annotations.SerializedName

//TODO 29 - Create DTO PhotoRemoteModel
data class PhotoRemoteModel(
    val camera: CameraRemoteModel,

    @field:SerializedName("earth_date")
    val earthDate : String,

    val id : Int,

    @field:SerializedName("img_src")
    val imgSrc : String,

    val rover : RoverRemoteModel,

    val sol : Int
)