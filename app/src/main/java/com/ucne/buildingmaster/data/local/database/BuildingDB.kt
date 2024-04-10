package com.ucne.buildingmaster.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucne.buildingmaster.data.local.dao.BuildingDao
import com.ucne.buildingmaster.data.local.entity.Vivienda

@Database(entities = [Vivienda::class], version = 3)
abstract class BuildingDB : RoomDatabase() {
    abstract fun edificioDAO(): BuildingDao
}
