package com.azrinurvani.jetpackcomposeforbeginner.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import com.azrinurvani.jetpackcomposeforbeginner.R
import com.azrinurvani.jetpackcomposeforbeginner.nav.Destinations.HOME

//TODO 15 - Create Routing and Action for Navigation because using Navigation Component
object Destinations {
    const val HOME = "home"
    const val MANIFEST = "manifest/{roverName}"
    //TODO 23 - Add new routing destinations and actions for Photo Screen with params roverName and sol
    const val PHOTO = "photo/{roverName}?sol={sol}"

    //TODO 43 - Create const value for SAVED route
    const val SAVED = "saved"
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

//TODO 42 - Create Sealed Class for item Navigation Page
sealed class Screen(
    val route : String,
    @StringRes val resourceId : Int,
    @DrawableRes val drawableId : Int
){
    object Home : Screen("home", R.string.rover,R.drawable.ic_rover)

    object Saved : Screen("Saved",R.string.saved,R.drawable.ic_save)
}