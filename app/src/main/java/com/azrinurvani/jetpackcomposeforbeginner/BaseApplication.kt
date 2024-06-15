package com.azrinurvani.jetpackcomposeforbeginner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//TODO 3 - Create BaseApplication extends Application with annotation @HiltAndroidApp
@HiltAndroidApp
class BaseApplication : Application() {
}