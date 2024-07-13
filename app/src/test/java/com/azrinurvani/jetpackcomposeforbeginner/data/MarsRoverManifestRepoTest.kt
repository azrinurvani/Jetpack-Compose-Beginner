package com.azrinurvani.jetpackcomposeforbeginner.data

import com.azrinurvani.jetpackcomposeforbeginner.MainCoroutineRule
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiState
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverManifestService
import com.azrinurvani.jetpackcomposeforbeginner.service.model.ManifestPhotoRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.PhotoManifestRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverManifestRemoteModel
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

//TODO 92 - Create MarsRoverManifestRepoTest class for Unit Test MarsRoverManifestRepo
class MarsRoverManifestRepoTest {

    //TODO 93 - Create rule of MainCoroutineRule (for IO Dispatchers)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    //TODO 94 - Create Mock Class using mockk to call MarsRoverManifestService class
    private val marsRoverManifestService = mockkClass(MarsRoverManifestService::class)

    //TODO 95 - Create function to test manifest service is successful
    @Test
    fun `should emmit success when manifest service is successful`() = runTest(coroutineRule.testDispatcher) {
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
                        cameras = listOf("camera1","camera2"),
                        earthDate = "2021-02-18",
                        sol = 0,
                        totalPhotos = 54
                    ),
                    ManifestPhotoRemoteModel(
                        cameras = listOf("camera2","camera3"),
                        earthDate = "2021-02-19",
                        sol = 1,
                        totalPhotos = 201
                    )
                ),
                status = "active",
                totalPhotos = 156687
            )
        )
        coEvery {
            marsRoverManifestService.getMarsRoverManifest("perseverance")
        } returns roverManifestRemoteModel

        //When
        val marsRoverManifestRepo = MarsRoverManifestRepo(marsRoverManifestService)
        val result = marsRoverManifestRepo.getMarsRoverManifest("perseverance").toList()

        //Then
        val expectedResult = RoverManifestUiState.Success(
            listOf(
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
            )
        )
        assertEquals(1,result.size)
        assertEquals(expectedResult,result[0])
    }

    //TODO 96 - create function to test manifest service throw exception
    @Test
    fun `should emmit error when mars service throw exception`() = runTest(coroutineRule.testDispatcher) {
        //Given
        coEvery {
            marsRoverManifestService.getMarsRoverManifest("perseverance")
        } throws TimeoutException()

        //When
        val marsRoverManifestRepo = MarsRoverManifestRepo(marsRoverManifestService)
        val result = marsRoverManifestRepo.getMarsRoverManifest("perseverance").toList()

        //Then
        assertEquals(1,result.size)
        assertEquals(RoverManifestUiState.Error,result[0])
    }

}