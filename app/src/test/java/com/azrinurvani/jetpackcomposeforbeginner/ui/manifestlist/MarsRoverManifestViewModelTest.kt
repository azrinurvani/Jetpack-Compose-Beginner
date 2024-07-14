package com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist

import com.azrinurvani.jetpackcomposeforbeginner.MainCoroutineRule
import com.azrinurvani.jetpackcomposeforbeginner.data.MarsRoverManifestRepo
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiState
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

//TODO 86 - Create MarsManifestViewModelTest Class for Unit Test MarsManifestViewModel
class MarsRoverManifestViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val marsRoverManifestRepo = mockkClass(MarsRoverManifestRepo::class)

    //TODO 91 - Implement function for testing Emmit class Error and Success when repo return Success/Error
    @Test
    fun `should emmit error when repo return error`() = runTest(coroutineRule.testDispatcher) {
        //Given
        coEvery {
            marsRoverManifestRepo.getMarsRoverManifest("perseverance")
        } returns flowOf(RoverManifestUiState.Error)

        //When
        val marsRoverManifestViewModel = MarsRoverManifestViewModel(marsRoverManifestRepo,coroutineRule.testDispatcher)
        marsRoverManifestViewModel.getMarsRoverManifest("perseverance")
        val result = marsRoverManifestViewModel.roverManifestUiState.first()

        //Then
        assertEquals(RoverManifestUiState.Error,result)
    }

    @Test
    fun `should emmit success when repo return success`() = runTest(coroutineRule.testDispatcher) {
        //Given
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
        coEvery {
            marsRoverManifestRepo.getMarsRoverManifest("perseverance")
        } returns flowOf(expectedResult)

        //When
        val marsRoverManifestViewModel = MarsRoverManifestViewModel(marsRoverManifestRepo,coroutineRule.testDispatcher)
        marsRoverManifestViewModel.getMarsRoverManifest("perseverance")
        val result = marsRoverManifestViewModel.roverManifestUiState.first()

        //Then
        assertEquals(expectedResult,result)

    }

}