package com.azrinurvani.jetpackcomposeforbeginner.service.model

import com.google.gson.annotations.SerializedName

data class RoverManifestRemoteModel(
    @field:SerializedName("photo_manifest")
    val photoManifest : PhotoManifestRemoteModel
)
