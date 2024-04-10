package com.ucne.buildingmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.ucne.buildingmaster.data.local.entity.Vivienda
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingDao {
    @Upsert
    suspend fun upsert(store: Vivienda)

    @Delete
    suspend fun delete(store: Vivienda)

    @Update
    suspend fun updateVivienda(vivienda: Vivienda)

    @Query("Select * From TableEdificio")
    fun getAll(): Flow<List<Vivienda>>

    @Query("SELECT * FROM tableedificio WHERE viviendaId = :id LIMIT 1")
    fun getById(id: Int): Flow<Vivienda?>

    @Query("SELECT * FROM TableEdificio WHERE tipo = :type")
    fun getByType(type: String): Flow<List<Vivienda>>
}
