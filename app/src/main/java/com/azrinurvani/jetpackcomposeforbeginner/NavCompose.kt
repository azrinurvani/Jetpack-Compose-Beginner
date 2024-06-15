package com.azrinurvani.jetpackcomposeforbeginner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.azrinurvani.jetpackcomposeforbeginner.nav.Action

import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations.HOME
import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations.MANIFEST
import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations.PHOTO
import com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist.MarsRoverManifestViewModel
import com.azrinurvani.jetpackcomposeforbeginner.ui.photolist.MarsRoverPhotoViewModel
import com.azrinurvani.jetpackcomposeforbeginner.ui.theme.JetpackComposeForBeginnerTheme
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.ManifestScreen
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.PhotoScreen
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.RoverList

//TODO 20 - Create function for Navigation Compose with implement All Screen using Navigation Compose
// such as RoverList and ManifestScreen
@Composable
fun NavCompose(){
    val navController = rememberNavController() // too call navController
    val actions = remember(navController){ //berfungsi untuk action destinations page
        Action(navController)
    }

    JetpackComposeForBeginnerTheme {
        NavHost(navController = navController, startDestination = HOME){
            composable(HOME){
                RoverList(){ roverName ->
                    actions.manifest(roverName)
                }
            }
            composable(MANIFEST){ backStageEntry->
                ManifestScreen(
                    //params
                    roverName = backStageEntry.arguments?.getString("roverName").toString(),
                    marsRoverManifestViewModel = hiltViewModel<MarsRoverManifestViewModel>(),
                    //TODO 26 - Add actions navigate to PhotoScreen as params
                    onClick = { roverName, sol ->
                        actions.photo(roverName,sol)
                    }
                )
            }
            //TODO 27 - Add composable for PhotoScreen
            composable(PHOTO){ backStageEntry ->
                PhotoScreen(
                    roverName = backStageEntry.arguments?.getString("roverName").toString(),
                    sol = backStageEntry.arguments?.getString("sol").toString(),
                    marsRoverPhotoViewModel = hiltViewModel<MarsRoverPhotoViewModel>()
                )
            }
        }
    }
}