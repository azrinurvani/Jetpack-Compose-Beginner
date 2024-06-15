package com.azrinurvani.jetpackcomposeforbeginner.domain.model

//TODO 11 - Create UI State when retrieve data from domain layer
sealed class RoverManifestUiState{
    data class Success(
        val roverManifestUiModelList: List<RoverManifestUiModel>

    ) : RoverManifestUiState()
    object Loading : RoverManifestUiState()
    object Error : RoverManifestUiState()
}

data class RoverManifestUiModel(
    val sol : String,
    val earthDate: String,
    val photoNumber : String
) : Comparable<RoverManifestUiModel>{
    override fun compareTo(other: RoverManifestUiModel): Int = other.earthDate.compareTo(this.earthDate)

}