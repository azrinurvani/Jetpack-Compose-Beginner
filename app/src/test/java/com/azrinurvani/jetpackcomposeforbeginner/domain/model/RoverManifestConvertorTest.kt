package com.azrinurvani.jetpackcomposeforbeginner.domain.model

import com.azrinurvani.jetpackcomposeforbeginner.service.model.ManifestPhotoRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.PhotoManifestRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverManifestRemoteModel
import org.junit.Assert.assertEquals
import org.junit.Test

//TODO 84 - Create Unit Test for Rover Manifest Convertor (fro RoverManifestRemoteModel to RoverManifestUiState)
class RoverManifestConvertorTest {

    @Test
    fun `should convert roverManifestRemoteModel to RoverManifestUiState`(){
        //Given
        val roverManifestRemoteModel = RoverManifestRemoteModel(
            photoManifest = PhotoManifestRemoteModel(
                landingDate = "2021-02-18",
                launchDate = "2020-07-30",
                maxDate = "2023-05-19",
                maxSol = "799",
                name = "Perseverance",
                photos = listOf(
                    ManifestPhotoRemoteModel(
                        cameras = listOf("REAR_HAZCAM_LEFT","REAR_HAZCAM_RIGHT"),
                        earthDate = "2021-02-18",
                        sol = 0,
                        totalPhotos = 54
                    ),
                    ManifestPhotoRemoteModel(
                        cameras = listOf("EDL_DDCAM","EDL_PUCAM2"),
                        earthDate = "2021-02-19",
                        sol = 1,
                        totalPhotos = 201
                    )
                ),
                status = "active",
                totalPhotos = 156877
            )
        )

        //When
        val result = toUiModel(roverManifestRemoteModel)

        //Then
        val expectedResult = RoverManifestUiState.Success(listOf(
            RoverManifestUiModel(
                sol = "1",
                earthDate = "2021-02-19",
                photoNumber = "201"
            ),
            RoverManifestUiModel(
                sol = "0",
                earthDate = "2021-02-18",
                photoNumber = "54"
            )
        ))

        assertEquals(expectedResult,result)
    }
}