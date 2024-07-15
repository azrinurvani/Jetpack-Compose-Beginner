package com.azrinurvani.jetpackcomposeforbeginner.data

import com.azrinurvani.jetpackcomposeforbeginner.MainCoroutineRule
import com.azrinurvani.jetpackcomposeforbeginner.db.MarsRoverSavedLocalModel
import com.azrinurvani.jetpackcomposeforbeginner.db.MarsRoverSavedPhotoDao
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import com.azrinurvani.jetpackcomposeforbeginner.service.MarsRoverPhotoService
import com.azrinurvani.jetpackcomposeforbeginner.service.model.CameraRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.PhotoRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverPhotoRemoteModel
import com.azrinurvani.jetpackcomposeforbeginner.service.model.RoverRemoteModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException

//TODO 104 - Create MarsPhotoTest for testing MarsPhotoRepo
class MarsPhotoRepoTest {

    //TODO 105 - Create variable to call Mars Photo Service and Mars Photo Saved Dao using mockk class
    private val marsRoverPhotoService = mockkClass(MarsRoverPhotoService::class)
    private val marsRoverSavedPhotoDao = mockkClass(MarsRoverSavedPhotoDao::class)

    //TODO 106 - Create variable to call MainCoroutineRule
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    //TODO 107 - Create function for testing when service and dao return valid data
    @Test
    fun `should emmit success when service and dao return valid data`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoRemoteModel = RoverPhotoRemoteModel(
            photos = listOf(
                //This configuration will define that the first photo is saved and not for the second one
                PhotoRemoteModel(
                    camera = CameraRemoteModel(
                        id = 1,
                        fullName = "Camera One",
                        name = "Camera 1",
                        roverId = 1
                    ),
                    earthDate = "2022-07-02",
                    id = 2,
                    imgSrc = "https://example.com/photo1",
                    rover = RoverRemoteModel(
                        id = 5,
                        landingDate = "2021-02-18",
                        launchDate = "2020-07-30",
                        name = "Perseverance",
                        status = "active"
                    ),
                    sol = 20
                ),
                PhotoRemoteModel(
                    camera = CameraRemoteModel(
                        id = 3,
                        fullName = "Camera Two",
                        name = "Camera 2",
                        roverId = 1
                    ),
                    earthDate = "2022-07-02",
                    id = 4,
                    imgSrc = "https://example.com/photo2",
                    rover = RoverRemoteModel(
                        id = 5,
                        landingDate = "2021-02-18",
                        launchDate = "2020-07-30",
                        name = "Perseverance",
                        status = "active"
                    ),
                    sol = 20
                )
            )
        )
        coEvery {
            marsRoverPhotoService.getMarsRoverPhoto("perseverance","0")
        } returns roverPhotoRemoteModel
        coEvery {
            marsRoverSavedPhotoDao.allSavedIds("0","perseverance")
        } returns flowOf(listOf(2))

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService,marsRoverSavedPhotoDao)
        val result = marsRoverPhotoRepo.getMarsRoverPhoto("perseverance","0").toList()

        //Then
        val expectedResult = RoverPhotoUiState.Success(
            listOf(
                RoverPhotoUiModel(
                    id = 2,
                    roverName = "Perseverance",
                    imgSrc = "https://example.com/photo1",
                    sol = "20",
                    earthDate = "2022-07-02",
                    cameraFullName = "Camera One",
                    isSaved = true
                ),
                RoverPhotoUiModel(
                    id = 4,
                    roverName = "Perseverance",
                    imgSrc = "https://example.com/photo2",
                    sol = "20",
                    earthDate = "2022-07-02",
                    cameraFullName = "Camera Two",
                    isSaved = false
                )
            )
        )

        assertEquals(1,result.size)
        assertEquals(expectedResult,result[0])
    }

    //TODO 108 - Create function for testing when service throw exception
    @Test
    fun `should emmit error when service throw exception`() = runTest(coroutineRule.testDispatcher) {
        //Given
        coEvery {
            marsRoverPhotoService.getMarsRoverPhoto("perseverance","0")
        } throws TimeoutException()
        coEvery {
            marsRoverSavedPhotoDao.allSavedIds("0","perseverance")
        } returns flowOf(listOf(2))

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService,marsRoverSavedPhotoDao)
        val result = marsRoverPhotoRepo.getMarsRoverPhoto("perseverance","0").toList()

        //Then
        assertEquals(1,result.size)
        assertEquals(RoverPhotoUiState.Error,result[0])
    }

    //TODO 109 - Create function for testing when saved is called
    @Test
    fun `should call insert when saved is called`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )
        val marsRoverSavedLocalModel = MarsRoverSavedLocalModel(
            roverPhotoId = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One"
        )
        coEvery {
            marsRoverSavedPhotoDao.insert(marsRoverSavedLocalModel)
        } returns Unit

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService,marsRoverSavedPhotoDao)
        marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)

        //Then
        coVerify { //use coVerify because given value no return any result
            marsRoverSavedPhotoDao.insert(marsRoverSavedLocalModel)
        }
    }

    //TODO 110 - Create function for delete data
    @Test
    fun `should call delete when remove is called`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One",
            isSaved = true
        )
        val marsRoverSavedLocalModel = MarsRoverSavedLocalModel(
            roverPhotoId = 2,
            roverName = "Perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "20",
            earthDate = "2022-07-02",
            cameraFullName = "Camera One"
        )
        coEvery {
            marsRoverSavedPhotoDao.delete(marsRoverSavedLocalModel)
        } returns Unit

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService,marsRoverSavedPhotoDao)
        marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)

        //Then
        coVerify { //use coVerify because given value no return any result
            marsRoverSavedPhotoDao.delete(marsRoverSavedLocalModel)
        }
    }

    //TODO 111 - Create function for retrieve all data from local
    @Test
    fun `should emmit ui model when all data are retrieved`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val marsRoverSavedLocalModelList = listOf(
            MarsRoverSavedLocalModel(
                roverPhotoId = 2,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo1",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera One"
            ),
            MarsRoverSavedLocalModel(
                roverPhotoId = 4,
                roverName = "Perseverance",
                imgSrc = "https://example.com/photo2",
                sol = "20",
                earthDate = "2022-07-02",
                cameraFullName = "Camera Two"
            ),
        )
        coEvery {
            marsRoverSavedPhotoDao.getAllSaved()
        } returns flowOf(marsRoverSavedLocalModelList)

        //When
        val marsRoverPhotoRepo = MarsRoverPhotoRepo(marsRoverPhotoService,marsRoverSavedPhotoDao)
        val result = marsRoverPhotoRepo.getAllSaved().first()

        //Then
        val expectedResult = RoverPhotoUiState.Success(
            roverPhotoUiModelList = listOf(
                RoverPhotoUiModel(
                    id = 2,
                    roverName = "Perseverance",
                    imgSrc = "https://example.com/photo1",
                    sol = "20",
                    earthDate = "2022-07-02",
                    cameraFullName = "Camera One",
                    isSaved = true
                ),
                RoverPhotoUiModel(
                    id = 4,
                    roverName = "Perseverance",
                    imgSrc = "https://example.com/photo2",
                    sol = "20",
                    earthDate = "2022-07-02",
                    cameraFullName = "Camera Two",
                    isSaved = true
                )
            )
        )
        assertEquals(expectedResult,result)
    }
}