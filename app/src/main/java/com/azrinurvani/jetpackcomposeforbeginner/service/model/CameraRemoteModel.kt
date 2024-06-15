package com.azrinurvani.jetpackcomposeforbeginner.service.model

import com.google.gson.annotations.SerializedName

//TODO 30 - Create DTO CameraRemoteModel
data class CameraRemoteModel(
    @field:SerializedName("full_name")
    val fullName : String,
    val id : Int,
    val name : String,

    @field:SerializedName("rover_id")
    val roverId : Int

)
