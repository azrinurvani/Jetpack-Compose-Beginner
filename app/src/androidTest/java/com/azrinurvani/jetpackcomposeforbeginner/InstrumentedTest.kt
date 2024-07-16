package com.azrinurvani.jetpackcomposeforbeginner

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiModel
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel
import com.azrinurvani.jetpackcomposeforbeginner.ui.theme.JetpackComposeForBeginnerTheme
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.ManifestList
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.PhotoList
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.RoverList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//TODO 112 - Refactor and rename ExperimentalInstrumentTest with InstrumentTest
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    //TODO 113 - Create variable for create compose rule using createComposeRule() method
    @get:Rule
    val composeTestRule = createComposeRule()

    //TODO 114 - Create function testRoverList() for testing function/method setContent() using composeTestRule
    @Test
    fun testRoverList(){
        //When
        composeTestRule.setContent {
            JetpackComposeForBeginnerTheme {
                RoverList(modifier = Modifier, onClick = {})
            }
        }

        //Then
        composeTestRule.onNodeWithText("Perseverance").assertIsDisplayed()
        composeTestRule.onNodeWithText("Curiosity").assertIsDisplayed()
        composeTestRule.onNodeWithText("Landing date: 03 June 2024").assertIsDisplayed()
        composeTestRule.onNodeWithText("Distance traveled : 12.56 km").assertIsDisplayed()
    }

    //TODO 115 - Create function testManifestList for test create UI ManifestList
    @Test
    fun testManifestList(){
        //Given
        val roverManifestUiList = listOf(
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

        //When
        composeTestRule.setContent {
            JetpackComposeForBeginnerTheme {
                ManifestList(
                    modifier = Modifier,
                    roverManifestUiModelList = roverManifestUiList,
                    roverName = "",
                    onClick = { _, _, -> }
                )
            }
        }

        //Then
        composeTestRule.onNodeWithText("Sol: 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth Date: 2021-02-18").assertIsDisplayed()
        composeTestRule.onNodeWithText("Number of Photo: 54").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth Date: 2021-02-19").assertIsDisplayed()
        composeTestRule.onNodeWithText("Number of Photo: 201").assertIsDisplayed()
    }

    //TODO 116 - Create function for testing UI PhotoList named testPhotoList()
    @Test
    fun testPhotoList(){
        //Given
        val roverPhotoUiModelList = listOf(
            RoverPhotoUiModel(
                id = 1,
                roverName = "perseverance",
                imgSrc = "https://example.com/photo1",
                sol = "0",
                earthDate = "2022-03-10",
                cameraFullName = "Camera One",
                isSaved = true
            ),
            RoverPhotoUiModel(
                id = 2,
                roverName = "perseverance",
                imgSrc = "https://example.com/photo2",
                sol = "1",
                earthDate = "2022-03-11",
                cameraFullName = "Camera Two",
                isSaved = false
            )
        )

        //When
        composeTestRule.setContent {
            JetpackComposeForBeginnerTheme {
                PhotoList(
                    modifier = Modifier,
                    roverPhotoUiModelList = roverPhotoUiModelList,
                    onClick = { _, ->}
                )
            }
        }

        //Then
        composeTestRule.onAllNodesWithContentDescription("Save Icon").assertCountEquals(2)
        composeTestRule.onNodeWithText("Sol: 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth Date: 2022-03-10").assertIsDisplayed()
        composeTestRule.onNodeWithText("Camera One").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sol: 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Earth Date: 2022-03-11").assertIsDisplayed()
        composeTestRule.onNodeWithText("Camera Two").assertIsDisplayed()

    }
}