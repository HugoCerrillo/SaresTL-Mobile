package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ekuipo.sarestl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalCredential(navController: NavController) {
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF1E3A8A)
    val white = Color.White

    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("Pagina principal", "Notificaciones", "Credencial Digital", "Historial de Registros", "Mi Perfil", "Cerrar Sesion")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Logos institucionales
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sep),
                                contentDescription = "Logo SEP",
                                modifier = Modifier.height(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.tecnm),
                                contentDescription = "Logo TECNM",
                                modifier = Modifier.height(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.tec),
                                contentDescription = "Logo Campus",
                                modifier = Modifier.height(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.sares),
                                contentDescription = "Logo SaresTL",
                                modifier = Modifier.height(32.dp)
                            )
                        }

                        // Iconos de usuario y menú
                        IconButton(onClick = { /* Sin funcionalidad */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.perfil),
                                contentDescription = "Perfil",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.menu),
                                    contentDescription = "Menú",
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .wrapContentWidth()
                            ) {
                                opciones.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            expanded = false
                                            when (opcion) {
                                                "Pagina principal" -> navController.navigate("home")
                                                "Notificaciones" -> navController.navigate("NotificationScreen")
                                                "Credencial Digital" -> navController.navigate("DigitalCredential")
                                                "Historial de Registros" -> navController.navigate("HistoryScreen")
                                                "Mi Perfil" -> navController.navigate("EditProfile")
                                                "Cerrar Sesión" -> navController.navigate("LoginScreen")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = white
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(lightBlue)
        ) {
            // Tarjeta principal de la credencial
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
                    .align(Alignment.Center),
                colors = CardDefaults.cardColors(containerColor = white),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Credencial
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = darkBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Foto de perfil
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.perfil),
                                    contentDescription = "Foto de perfil",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(80.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Nombre
                            Text(
                                text = "Joseph Alexander Martínez Cortés",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = white,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Número de identificación
                            Text(
                                text = "21240551",
                                style = MaterialTheme.typography.titleMedium,
                                color = white,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Código de barras
                            Box(
                                modifier = Modifier
                                    .background(white, RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.barcode),
                                        contentDescription = "Código de barras",
                                        modifier = Modifier
                                            .height(60.dp)
                                            .fillMaxWidth()
                                    )

                                    Text(
                                        text = "21240551",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Copyright
                    Text(
                        text = "© SaresTL 2025",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}