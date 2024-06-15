package com.azrinurvani.jetpackcomposeforbeginner.domain.model

import com.azrinurvani.jetpackcomposeforbeginner.R

//TODO 9 - Create model for domain layer named RoverUiModelCreator to create list of RoverUIModel
val roverUiModelList  = listOf(
    RoverUiModel("Perseverance", R.drawable.perseverance, landingDate = "03 June 2024", distance = "12.56 km"),
    RoverUiModel("Curiosity", R.drawable.curiosity, landingDate = "04 June 2019", distance = "13.56 km"),
    RoverUiModel("Opportunity", R.drawable.opportunity, landingDate = "01 January 2022", distance = "23.56 km"),
    RoverUiModel("Spirit", R.drawable.spirit, landingDate = "28 November 2024", distance = "33.56 km"),
)