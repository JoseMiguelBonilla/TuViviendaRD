package com.ucne.buildingmaster.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import com.ucne.buildingmaster.util.FileUtil
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEdificio(
    viewModel: ViviendaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val _state = state.vivienda
    var expanded by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            val filePath = uri?.let { FileUtil.saveImage(context, it) }
            filePath?.let { ViviendaEvent.UrlImagen(it) }?.let { viewModel.onEvent(it) }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Blue
                ),
                title = {
                    Row {
                        /*IconButton(
                            onClick = { navigateBack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Back",
                                tint = androidx.compose.ui.graphics.Color.Black,
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }*/
                        Spacer(modifier = Modifier.width(14.dp))
                        Row {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "add",
                                tint = Color.White,
                                modifier = Modifier.rotate(268f)
                            )
                            Text(
                                text = "Registro de Edificio",
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "add",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 70.dp),
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            if (_state.imgUrl.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(_state.imgUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Surface(
                onClick = {
                    launcher.launch("image/*")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .height(50.dp),
                color = Color.Gray
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar Imagen",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                    Text("Agregar Imagen", color = Color.White)
                }
            }
            if (state.emptyFields.contains("Imagen")) {
                Text(text = "La Imagen es Requerida", color = Color.Red)
            }

            OutlinedTextField(
                value = _state.nombre,
                onValueChange = { viewModel.onEvent(ViviendaEvent.Nombre(it)) },
                label = { Text(text = "Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            if (state.emptyFields.contains("Nombre")) {
                Text(text = "El Nombre es Requerido", color = androidx.compose.ui.graphics.Color.Red)
            }

            OutlinedTextField(
                value = _state.direccion,
                onValueChange = { viewModel.onEvent(ViviendaEvent.Direccion(it)) },
                label = { Text(text = "Dirección") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            if (state.emptyFields.contains("Dirección")) {
                Text(text = "La Dirección es Requerida", color = androidx.compose.ui.graphics.Color.Red)
            }

            Column {
                OutlinedTextField(
                    value = selectedType,
                    onValueChange = { selectedType = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .onGloballyPositioned { coordinates ->
                            textfieldSize = coordinates.size.toSize()
                        },
                    readOnly = true,
                    label = { Text("Tipo de Vivienda") },
                    trailingIcon = {
                        Icon(
                            icon,
                            "Toggle Dropdown",
                            Modifier.clickable { expanded = !expanded }
                        )
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                ) {
                    listOf("Casa", "Apartamento").forEach { tipo ->
                        DropdownMenuItem(onClick = {
                            selectedType = tipo
                            expanded = false
                            viewModel.onEvent(ViviendaEvent.Tipo(tipo))
                        },
                            text = {
                                Text(text = tipo)
                            }
                        )
                    }
                }
            }
            if (state.emptyFields.contains("Tipo")) {
                Text(text = "El Tipo de Vivienda es Requerido", color = Color.Red)
            }

            OutlinedTextField(
                value = _state.precio.toString(),
                onValueChange = { viewModel.onEvent(ViviendaEvent.Precio(it)) },
                label = { Text(text = "Precio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Next
                )
            )
            if (state.emptyFields.contains("Precio")) {
                Text(text = "El Precio es Requerido", color = Color.Red)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(ViviendaEvent.onNew)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Nuevo")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nuevo")
                    }
                }

                Button(
                    onClick = {
                        viewModel.onEvent(ViviendaEvent.onSave)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Guardar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Guardar")
                    }
                }
            }
        }
        state.succesMessage?.let {
            MessageCard(message = it, color = androidx.compose.ui.graphics.Color.Gray)
        }
    }
}

@Composable
fun MessageCard(
    message: String,
    color: Color,
    durationMillis: Long = 3000
) {
    var showMessage by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        showMessage = false
    }

    if (showMessage) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(16.dp),
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = color
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
