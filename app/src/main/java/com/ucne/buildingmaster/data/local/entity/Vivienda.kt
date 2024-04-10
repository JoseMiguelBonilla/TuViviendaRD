package com.ucne.buildingmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ("TableEdificio")
data class Vivienda(
    @PrimaryKey(autoGenerate = true)
    val viviendaId: Int = 0,
    val nombre: String = "",
    val direccion: String = "",
    val imgUrl: String = "",
    val tipo: String = "",
    val disponible: Boolean = true,
    val precio: Float = 0f
)
