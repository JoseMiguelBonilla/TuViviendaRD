package com.ucne.buildingmaster.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ucne.buildingmaster.ui.screens.RegistroEdificio
import com.ucne.buildingmaster.ui.screens.apartmentScreen.viviendaScreen
import com.ucne.buildingmaster.ui.screens.detailScreen.detailVivienda

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun navigation() {
    val navController = rememberNavController()
    val myBrown = Color(android.graphics.Color.parseColor("#DC7D3D"))

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = myBrown
                ),
                title = {
                    Text(text = "BuildingMaster")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("registro")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Vivienda"
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate("vivienda")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Consulta de vivienda"
                        )
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "vivienda",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("registro") {
                RegistroEdificio()
            }
            composable("vivienda") {
                viviendaScreen(navController = navController)
            }
            composable(
                route = "detalle/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("id")
                productId?.let { viviedaId ->
                    detailVivienda(viviedaId, navController = navController)
                }
            }
        }
    }
}
