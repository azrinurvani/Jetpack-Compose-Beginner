package com.azrinurvani.jetpackcomposeforbeginner.service.model

import com.google.gson.annotations.SerializedName

//TODO 6 - Create DTO from PhotoManifest from Response API
data class ManifestPhotoRemoteModel(
    val cameras : List<String>,

    @field:SerializedName("earth_date")
    val earthDate : String,

    val sol : Int,

    @field:SerializedName("total_photos")
    val totalPhotos : Int

)
