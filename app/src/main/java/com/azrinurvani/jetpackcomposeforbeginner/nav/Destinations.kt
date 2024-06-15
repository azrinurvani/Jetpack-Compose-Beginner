package com.azrinurvani.jetpackcomposeforbeginner.nav

import androidx.navigation.NavController
import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations.HOME

//TODO 15 - Create Routing and Action for Navigation because using Navigation Component
object Destinations {
    const val HOME = "home"
    const val MANIFEST = "manifest/{roverName}"
    //TODO 23 - Add new routing destinations and actions for Photo Screen with params roverName and sol
    const val PHOTO = "photo/{roverName}?sol={sol}"
}

class Action(navController: NavController){
    val home : () -> Unit = { navController.navigate(HOME) }
    val manifest : (roverName : String) -> Unit = { roverName ->
        navController.navigate("manifest/${roverName}")
    }
    //TODO 23 - Add new routing destinations and actions for Photo Screen with params roverName and sol
    val photo : (roverName :String,sol:String) -> Unit = { roverName, sol ->  
        navController.navigate("photo/${roverName}?sol=${sol}")
    }
}