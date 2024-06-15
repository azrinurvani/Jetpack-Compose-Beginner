package com.azrinurvani.jetpackcomposeforbeginner.nav

import androidx.navigation.NavController
import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations.HOME

//TODO 15 - Create Routing and Action for Navigation because using Navigation Component
object Destinations {
    const val HOME = "home"
    const val MANIFEST = "manifest/{roverName}"
}

class Action(navController: NavController){
    val home : () -> Unit = { navController.navigate(HOME) }
    val manifest : (roverName : String) -> Unit = { roverName ->
        navController.navigate("manifest/${roverName}")
    }
}