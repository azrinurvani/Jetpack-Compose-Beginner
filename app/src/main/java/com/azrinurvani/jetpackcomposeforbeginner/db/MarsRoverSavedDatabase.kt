package com.azrinurvani.jetpackcomposeforbeginner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//TODO 49 - Create instance database to build Room Database
@Database(entities = [MarsRoverSavedLocalModel::class], exportSchema = false, version = 1)
abstract class MarsRoverSavedDatabase : RoomDatabase(){

    abstract fun marsRoverSavedPhotoDao() : MarsRoverSavedPhotoDao

    companion object {

        @Volatile
        private var INSTANCE : MarsRoverSavedDatabase? = null

        fun getInstance(context: Context) : MarsRoverSavedDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: buildDatabase(context).also{
                    INSTANCE = it
                }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                MarsRoverSavedDatabase::class.java,
                "marsRover.db"
            ).fallbackToDestructiveMigration() //TO DELETE Database when version upgraded
                .build()
    }
}