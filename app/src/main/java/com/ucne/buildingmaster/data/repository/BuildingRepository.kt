package com.ucne.buildingmaster.data.repository

import com.ucne.buildingmaster.data.local.dao.BuildingDao
import com.ucne.buildingmaster.data.local.entity.Vivienda
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BuildingRepository @Inject constructor(
    private val builDao: BuildingDao
) {
    suspend fun upsert(vivienda: Vivienda) {
        builDao.upsert(vivienda)
    }

    suspend fun delete(vivienda: Vivienda) {
        builDao.delete(vivienda)
    }

    fun getEdificio(): Flow<List<Vivienda>> {
        return builDao.getAll()
    }

    fun getEdificio(id: Int): Flow<Vivienda?> {
        return builDao.getById(id)
    }

    suspend fun updateVivienda(vivienda: Vivienda) {
        builDao.updateVivienda(vivienda)
    }

    fun getProductosByType(type: String): Flow<List<Vivienda>> {
        return builDao.getByType(type)
    }
}
