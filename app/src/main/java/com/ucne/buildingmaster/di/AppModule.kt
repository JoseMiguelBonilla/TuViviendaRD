package com.ucne.buildingmaster.di

import android.content.Context
import androidx.room.Room
import com.ucne.buildingmaster.data.local.database.BuildingDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesBuildingDatabase(@ApplicationContext appContext: Context): BuildingDB =
        Room.databaseBuilder(appContext,BuildingDB::class.java, "building.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideBuildingDao(db: BuildingDB) = db.edificioDAO()
}
