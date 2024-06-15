package com.azrinurvani.jetpackcomposeforbeginner.service.model

import com.google.gson.annotations.SerializedName

//TODO 6 - Create DTO from PhotoManifest from Response API
data class RoverManifestRemoteModel(
    @field:SerializedName("photo_manifest")
    val photoManifest : PhotoManifestRemoteModel
)
