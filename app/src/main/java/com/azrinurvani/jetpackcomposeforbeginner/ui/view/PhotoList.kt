package com.azrinurvani.jetpackcomposeforbeginner.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.azrinurvani.jetpackcomposeforbeginner.R
import com.azrinurvani.jetpackcomposeforbeginner.domain.model.RoverPhotoUiModel

//TODO 38 - Create PhotoList UI
@Composable
fun PhotoList(
    //TODO 46 - Create modifier for All Page (to handle stuck when scroll to bottom)
    modifier: Modifier,
    roverPhotoUiModelList: List<RoverPhotoUiModel>,
    onClick : (roverPhotoUiModel : RoverPhotoUiModel) -> Unit
) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(
                count = roverPhotoUiModelList.size, itemContent = { index ->
                    Photo(roverPhotoUiModel = roverPhotoUiModelList[index], onClick)
                })
        }
    }
}

@Composable
fun Photo(
    roverPhotoUiModel: RoverPhotoUiModel,
    //TODO 55 - Add onClick to create action after card of photo is clicked
    onClick : (roverPhotoUiModel : RoverPhotoUiModel) -> Unit
){
    Card(
        //TODO 56 - Add modifier.clickable to trigger onClick listener
        modifier = Modifier.padding(16.dp).clickable {
            onClick(roverPhotoUiModel)
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //TODO 57 - Add row widget for icon save
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(
                    id = if (roverPhotoUiModel.isSaved) {
                        R.drawable.ic_save
                    } else {
                        R.drawable.ic_save_outline
                    }
                ), contentDescription = "Save Icon" )
                Text(
                    text = roverPhotoUiModel.roverName,
                    modifier = Modifier.padding(16.dp)
                )
            }
            AsyncImage(
                model = roverPhotoUiModel.imgSrc,
                contentDescription = "rover_image",
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()

            )
            Text(text = stringResource(id = R.string.sol,roverPhotoUiModel.sol))
            Text(text = stringResource(id = R.string.earth_date,roverPhotoUiModel.earthDate))
            Text(text = roverPhotoUiModel.cameraFullName)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoPreview(){
    //using fake model just for preview
    Photo(
        roverPhotoUiModel = RoverPhotoUiModel(
            id = 4,
            roverName = "Curiosity",
            imgSrc = "https://domain.com",
            sol = "34",
            earthDate = "2021-03-02",
            cameraFullName = "Camera Azri Nurvan - Right",
            true
        )
    ){

    }
}