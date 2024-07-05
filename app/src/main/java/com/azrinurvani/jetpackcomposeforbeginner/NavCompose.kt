package com.azrinurvani.jetpackcomposeforbeginner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.azrinurvani.jetpackcomposeforbeginner.nav.Action
import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations
import com.azrinurvani.jetpackcomposeforbeginner.nav.Screen
import com.azrinurvani.jetpackcomposeforbeginner.ui.manifestlist.MarsRoverManifestViewModel
import com.azrinurvani.jetpackcomposeforbeginner.ui.photolist.MarsRoverPhotoViewModel
import com.azrinurvani.jetpackcomposeforbeginner.ui.savedlist.MarsRoverSavedViewModel
import com.azrinurvani.jetpackcomposeforbeginner.ui.theme.JetpackComposeForBeginnerTheme
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.ManifestScreen
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.PhotoListSavedScreen
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.PhotoScreen
import com.azrinurvani.jetpackcomposeforbeginner.ui.view.RoverList

//TODO 20 - Create function for Navigation Compose with implement All Screen using Navigation Compose
// such as RoverList and ManifestScreen
@Composable
fun NavCompose(){

    //TODO 44 - Create items variable for list items of NavigationBar
    val items = arrayOf(
        Screen.Home,
        Screen.Saved
    )

    val navController = rememberNavController() // too call navController
    val actions = remember(navController){ //berfungsi untuk action destinations page
        Action(navController)
    }

    JetpackComposeForBeginnerTheme {
        //TODO 45 - Move all nav host to Scaffold and implement NavigationBar
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.drawableId),
                                    contentDescription = stringResource(id = screen.resourceId)
                                )
                            },
                            label = {
                                Text(text = stringResource(id = screen.resourceId))
                            },
                            selected = currentDestination?.hierarchy?.any{
                                if (it.route?.contains("manifest") == true || it.route?.contains("photo") == true){
                                    screen.route == "home"
                                }else{
                                    it.route == screen.route
                                }
                            } == true,
                            onClick = {
                                navController.navigate(screen.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }

        ) { innerPadding->
            //TODO 46 - Create modifier for All Page (to handle stuck when scroll to bottom)
            val modifier = Modifier.padding(innerPadding)


            NavHost(navController = navController, startDestination = Destinations.HOME){
                composable(Destinations.HOME){
                    RoverList(
                        modifier = modifier
                    ){ roverName ->
                        actions.manifest(roverName)
                    }
                }
                composable(Destinations.MANIFEST){ backStageEntry->
                    ManifestScreen(
                        //params
                        modifier = modifier,
                        roverName = backStageEntry.arguments?.getString("roverName").toString(),
                        marsRoverManifestViewModel = hiltViewModel<MarsRoverManifestViewModel>(),
                        //TODO 26 - Add actions navigate to PhotoScreen as params
                        onClick = { roverName, sol ->
                            actions.photo(roverName,sol)
                        }
                    )
                }
                //TODO 27 - Add composable for PhotoScreen
                composable(Destinations.PHOTO){ backStageEntry ->
                    PhotoScreen(
                        modifier = modifier,
                        roverName = backStageEntry.arguments?.getString("roverName").toString(),
                        sol = backStageEntry.arguments?.getString("sol").toString(),
                        marsRoverPhotoViewModel = hiltViewModel<MarsRoverPhotoViewModel>()
                    )
                }

                composable(Destinations.SAVED){ backStageEntry ->
                    PhotoListSavedScreen(
                        modifier = modifier,
                        marsRoverSavedViewModel = hiltViewModel<MarsRoverSavedViewModel>()
                    )
                }
            }

        }


    }
}