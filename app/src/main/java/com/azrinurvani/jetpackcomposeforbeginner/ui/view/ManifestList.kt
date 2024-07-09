package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azrinurvani.jetpackcomposeforbeginner.R
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverManifestUiModel

//TODO 18 - Create UI for Card from ManifestList
@Composable
fun ManifestList(
    //TODO 46 - Create modifier for All Page (to handle stuck when scroll to bottom)
    modifier: Modifier,
    //TODO 24 - Modify Params with add new params called roverName and onCLick (Unit Function)
    roverManifestUiModelList: List<RoverManifestUiModel>,
    roverName:String,
    onClick : (roverName:String,sol:String) -> Unit
){
    Surface(
        color = MaterialTheme.colorScheme.background,
        //TODO 46 - Create modifier for All Page (to handle stuck when scroll to bottom)
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(
                count = roverManifestUiModelList.size,
                itemContent = { index ->
                    //TODO 24 - Modify Params with add new params called roverName and onCLick (Unit Function)
                    Manifest(
                        roverManifestUiModel = roverManifestUiModelList[index],
                        roverName,
                        onClick
                    )
                }
            )
        }
    }
}

@Composable
fun Manifest(
    //TODO 24 - Modify Params with add new params called roverName and onCLick (Unit Function)
    roverManifestUiModel: RoverManifestUiModel,
    roverName:String,
    onClick : (roverName:String,sol:String) -> Unit
){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                //TODO 25 - Pass value onClick params here
                onClick(roverName,roverManifestUiModel.sol)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
//            Text(text = stringResource(id = R.string.sol,roverManifestUiModel.sol) )
//            Text(text = stringResource(id = R.string.earth_date,roverManifestUiModel.earthDate))
//            Text(text = stringResource(id = R.string.number_of_photo,roverManifestUiModel.photoNumber))
            //TODO 80 - Implement MaterialTheme.typography to widget UI
            Text(
                text = stringResource(id = R.string.sol,roverManifestUiModel.sol),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = R.string.earth_date,roverManifestUiModel.earthDate),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(
                    id = R.string.number_of_photo,
                    roverManifestUiModel.photoNumber
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManifestPreview(){
    Manifest(
        roverManifestUiModel = RoverManifestUiModel(
            sol = "4",
            earthDate = "2024-06-09",
            photoNumber = "20"
        ),
        roverName = "",
        onClick = { _ ,_ -> }
    )
}