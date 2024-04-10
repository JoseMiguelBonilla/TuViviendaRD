package com.ucne.buildingmaster.ui.screens.apartmentScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ucne.buildingmaster.data.local.entity.Vivienda
import com.ucne.buildingmaster.ui.screens.ViviendaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun viviendaScreen(
    viewModel: ViviendaViewModel = hiltViewModel(),
    navController: NavController
) {
    val viviendas by viewModel.vivienda.collectAsState(initial = emptyList())

    val casas = viviendas.filter { it.tipo == "Casa" }
    val apartamentos = viviendas.filter { it.tipo == "Apartamento" }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Apartamento",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, end = 13.dp)
        ) {
            items(apartamentos) { vivienda ->
                apartmentCard(
                    vivienda = vivienda,
                    onClick = { navController.navigate("detalle/${vivienda.viviendaId}") }
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Casa",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(5.dp)
        ) {
            items(casas) { casa ->
                viviendaCard(
                    vivienda = casa,
                    onClick = { navController.navigate("detalle/${casa.viviendaId}") },
                )
            }
        }
    }
}

@Composable
fun apartmentCard(
    vivienda: Vivienda,
    onClick: () -> Unit,
) {
    val painter: Painter = rememberImagePainter(data = vivienda.imgUrl)
    val myGreen = Color(android.graphics.Color.parseColor("#04764B"))

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp) // Limitando el ancho de la tarjeta
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
        ) {
            Box(
                modifier = Modifier.aspectRatio(1f)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize() // Haciendo que la imagen ocupe todo el espacio disponible
                        .clip(MaterialTheme.shapes.medium)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = vivienda.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Text(
                                    text = String.format("%.2f", vivienda.precio),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = myGreen
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    text = if (vivienda.disponible == true) "🟢" else "🚫",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun viviendaCard(
    vivienda: Vivienda,
    onClick: () -> Unit,
) {
    val painter: Painter = rememberImagePainter(data = vivienda.imgUrl)
    val myGreen = Color(android.graphics.Color.parseColor("#04764B"))

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
        ) {
            Box(
                modifier = Modifier.aspectRatio(1f)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(175.dp)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = vivienda.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Text(
                                    text = String.format("%.2f", vivienda.precio),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = myGreen
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    text = if (vivienda.disponible == true) "🟢" else "🚫",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}