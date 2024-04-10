package com.ucne.buildingmaster.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.buildingmaster.data.local.entity.Vivienda
import com.ucne.buildingmaster.data.repository.BuildingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViviendaViewModel @Inject constructor(
    private val edificioRepository: BuildingRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ViviendaState())
    val state = _state.asStateFlow()
    val vivienda: Flow<List<Vivienda>> = edificioRepository.getEdificio()

    fun onEvent(event: ViviendaEvent) {
        when (event) {
            is ViviendaEvent.ViviendaId -> {
                _state.update {
                    it.copy(
                        vivienda = it.vivienda.copy(
                            viviendaId = event.edificioId.toIntOrNull() ?: 0
                        )
                    )
                }
            }

            is ViviendaEvent.Nombre -> {
                _state.update {
                    it.copy(
                        vivienda = it.vivienda.copy(nombre = event.nombre)
                    )
                }
            }

            is ViviendaEvent.Direccion -> {
                _state.update {
                    it.copy(
                        vivienda = it.vivienda.copy(direccion = event.direccion)
                    )
                }
            }

            is ViviendaEvent.UrlImagen -> {
                _state.update {
                    it.copy(
                        vivienda = it.vivienda.copy(imgUrl = event.url)
                    )
                }
            }

            is ViviendaEvent.Tipo -> {
                _state.update {
                    it.copy(
                        vivienda = it.vivienda.copy(tipo = event.tipo)
                    )
                }
            }

            is ViviendaEvent.Precio -> {
                _state.update {
                    it.copy(
                        vivienda = it.vivienda.copy(precio = event.precio.toFloatOrNull() ?: 0f)
                    )
                }
            }

            ViviendaEvent.onSave -> {
                val nombre = state.value.vivienda.nombre
                val direccion = state.value.vivienda.direccion
                val imgUrl = state.value.vivienda.imgUrl
                val tipo = state.value.vivienda.tipo
                val precio = state.value.vivienda.precio

                val emptyFields = mutableListOf<String>()
                if (nombre.isBlank()) {
                    emptyFields.add("Nombre")
                }
                if (direccion.isBlank()) {
                    emptyFields.add("Dirección")
                }
                if (imgUrl.isBlank()) {
                    emptyFields.add("Imagen")
                }
                if (tipo.isBlank()) {
                    emptyFields.add("Tipo")
                }
                if (precio <= 0) {
                    emptyFields.add("Precio")
                }

                if (emptyFields.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            error = "Llene los campos requeridos: ${emptyFields.joinToString(", ")}",
                            emptyFields = emptyFields
                        )
                    }
                    return
                }

                val vivienda = Vivienda(
                    viviendaId = state.value.vivienda.viviendaId,
                    nombre = nombre,
                    direccion = direccion,
                    imgUrl = imgUrl,
                    tipo = tipo,
                    precio = precio
                )

                _state.update {
                    it.copy(
                        isLoading = true,
                        error = null,
                        succesMessage = null
                    )
                }

                viewModelScope.launch {
                    try {
                        edificioRepository.upsert(vivienda)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                succesMessage = "Se guardó correctamente✔"
                            )
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = "Error al guardar: ${e.message}"
                            )
                        }
                    }
                }

                _state.update {
                    it.copy(
                        vivienda = Vivienda(),
                        emptyFields = listOf()
                    )
                }
            }

            ViviendaEvent.onNew -> {
                _state.update {
                    it.copy(
                        succesMessage = "\t\t\t\t\t\t\tℹVacíoℹ",
                        vivienda = Vivienda(),
                        emptyFields = listOf()
                    )
                }
            }
        }
    }

    fun getEdificios(): Flow<List<Vivienda>> {
        return edificioRepository.getEdificio()
    }

    fun getEdificioById(edificioId: Int): Flow<Vivienda?> {
        return edificioRepository.getEdificio(edificioId)
    }

    fun rentarAhora(viviendaId: Int) {
        viewModelScope.launch {
            try {
                val vivienda = edificioRepository.getEdificio(viviendaId).firstOrNull()
                vivienda?.let {
                    val updatedVivienda = it.copy(disponible = false)
                    edificioRepository.updateVivienda(updatedVivienda)
                }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}

data class ViviendaState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val vivienda: Vivienda = Vivienda(),
    val succesMessage: String? = null,
    val emptyFields: List<String> = listOf()
)

sealed interface ViviendaEvent {
    data class ViviendaId(val edificioId: String) : ViviendaEvent
    data class Nombre(val nombre: String) : ViviendaEvent
    data class Direccion(val direccion: String) : ViviendaEvent
    data class UrlImagen(val url: String) : ViviendaEvent
    data class Tipo(val tipo: String) : ViviendaEvent
    data class Precio(val precio: String) : ViviendaEvent
    object onSave : ViviendaEvent
    object onNew : ViviendaEvent
}