package com.azrinurvani.jetpackcomposeforbeginner.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//TODO 48 - Create Dao for DBMS local
@Dao
interface MarsRoverSavedPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marsRoverSavedLocalModel: MarsRoverSavedLocalModel)

    @Delete
    suspend fun delete(marsRoverSavedLocalModel: MarsRoverSavedLocalModel)

    @Query("SELECT roverPhotoId FROM rover_photo WHERE sol = :sol AND roverName = :roverName")
    fun allSavedIds(sol:String, roverName: String) : Flow<List<Int>>



}