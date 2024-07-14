package com.azrinurvani.jetpackcomposeforbeginner.ui.photolist

import com.azrinurvani.jetpackcomposeforbeginner.MainCoroutineRule
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverPhotoRepo
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

//TODO 97 - Create MarsRoverPhotoViewModelTest class
class MarsRoverPhotoViewModelTest {

    //TODO 98 - Create variable coroutineRule and initiate with MainCoroutineRule()
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    //TODO 99 - Create variable marsRoverPhotoRepo using Mock Class to Call MarsRoverPhotoRepo class
    private val marsRoverPhotoRepo = mockkClass(MarsRoverPhotoRepo::class)


    //TODO 100 - Create function for testing function getMarsRoverPhoto in MarsPhotoRoverViewModel
    @Test
    fun `should emmit success when repository emmit success`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val expectedResult = RoverPhotoUiState.Success(
            roverPhotoUiModelList = listOf(
                RoverPhotoUiModel(
                    id = 1,
                    roverName = "perseverance",
                    imgSrc = "https://example.com/photo1",
                    sol = "0",
                    earthDate = "2022-03-10",
                    cameraFullName = "camera one",
                    isSaved = true
                ),
                RoverPhotoUiModel(
                    id = 2,
                    roverName = "perseverance",
                    imgSrc = "https://example.com/photo2",
                    sol = "0",
                    earthDate = "2022-03-10",
                    cameraFullName = "camera two",
                    isSaved = false
                )
            )
        )
        coEvery {
            marsRoverPhotoRepo.getMarsRoverPhoto("perseverance","0")
        } returns flowOf(expectedResult)

        //When
        val marsRoverPhotoViewModel = MarsRoverPhotoViewModel(marsRoverPhotoRepo,coroutineRule.testDispatcher)
        marsRoverPhotoViewModel.getMarsRoverPhoto("perseverance","0")
        val result = marsRoverPhotoViewModel.roverPhotoUiState.first()

        //Then
        assertEquals(expectedResult,result)
    }

    //TODO 101 - Create function for testing function getMarsRoverPhoto when return Error
    @Test
    fun `should emmit error when repository emmit error`() = runTest(coroutineRule.testDispatcher) {
        //Given
        coEvery {
            marsRoverPhotoRepo.getMarsRoverPhoto("perseverance","0")
        } returns flowOf(RoverPhotoUiState.Error)

        //When
        val marsRoverPhotoViewModel = MarsRoverPhotoViewModel(marsRoverPhotoRepo,coroutineRule.testDispatcher)
        marsRoverPhotoViewModel.getMarsRoverPhoto("perseverance","0")
        val result = marsRoverPhotoViewModel.roverPhotoUiState.first()

        //Then
        assertEquals(RoverPhotoUiState.Error,result)
    }

    //TODO 102 - Create function for testing function changeSaveStatus() in MarsRoverPhotoViewModel when remove photo
    @Test
    fun `should remove photo when photo is saved and change save status is called`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 1,
            roverName = "perseverance",
            imgSrc = "https://example.com/photo1",
            sol = "0",
            earthDate = "2022-03-10",
            cameraFullName = "Camera One",
            isSaved = true
        )

        coEvery {
            marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)
        } returns Unit

        //When
        val marsPhotoVieModel = MarsRoverPhotoViewModel(marsRoverPhotoRepo,coroutineRule.testDispatcher)
        marsPhotoVieModel.changeSaveStatus(roverPhotoUiModel)

        //Then
        coVerify { //using coVerify because the function does not call any result
            marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)
        }
    }


    //TODO 103 - Create function for testing function changeSaveStatus() in MarsRoverPhotoViewModel when save photo
    @Test
    fun `should save photo when photo is not saved and change save status is called`() = runTest(coroutineRule.testDispatcher) {
        //Given
        val roverPhotoUiModel = RoverPhotoUiModel(
            id = 1,
            roverName = "perseverance",
            imgSrc = "https://example.com/photo2",
            sol = "0",
            earthDate = "2022-03-10",
            cameraFullName = "Camera Two",
            isSaved = false
        )

        coEvery {
            marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)
        } returns Unit

        //When
        val marsPhotoVieModel = MarsRoverPhotoViewModel(marsRoverPhotoRepo,coroutineRule.testDispatcher)
        marsPhotoVieModel.changeSaveStatus(roverPhotoUiModel)

        //Then
        coVerify { //using coVerify because the function does not call any result
            marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)
        }
    }


}