package com.ekuipo.sarestl.userinterface

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ekuipo.sarestl.R
import com.ekuipo.sarestl.models.SessionManager
import androidx.compose.ui.platform.LocalContext


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    // Varuables de session

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val clave = remember { sessionManager.getUserKey() }
    val name = remember { sessionManager.getUserName() }
    val userType = remember { sessionManager.getUserRol() }


    // Definir los colores que coinciden con la interfaz web
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
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
                .verticalScroll(rememberScrollState())
        ) {
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
                Spacer(modifier = Modifier.height(32.dp))

                // Mensaje de bienvenida
                Text(
                    text = "Bienvenido (a): $name",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Información del usuario
                Text(
                    text = "Tu clave de sesión es: $clave",
                    //text = "Eres un: Administrador en TECNM Campus León Campus 1",
                    style = MaterialTheme.typography.bodyLarge,
                    color = darkBlue,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Rol del usuario
                Text(
                    text = "Con rol en nuestro sistema de: $userType",
                    style = MaterialTheme.typography.bodyLarge,
                    color = darkBlue,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { /* Sin funcionalidad */ },
                        colors = ButtonDefaults.buttonColors(containerColor = darkBlue),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Ver Perfil",
                            color = white,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    Button(
                        onClick = { /* Sin funcionalidad */ },
                        colors = ButtonDefaults.buttonColors(containerColor = darkBlue),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Configuraciones",
                            color = white,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Tarjetas informativas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Tarjeta de Noticias
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .height(180.dp),
                        colors = CardDefaults.cardColors(containerColor = white)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Noticias",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = darkBlue
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Actualiza tus conocimientos con las últimas noticias del campus y el sistema.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Tarjeta de Accesos Rápidos
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .height(180.dp),
                        colors = CardDefaults.cardColors(containerColor = white)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Accesos Rápidos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = darkBlue
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Accede rápidamente a los recursos más utilizados en el sistema.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Tarjeta de Eventos
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .height(180.dp),
                        colors = CardDefaults.cardColors(containerColor = white)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Eventos",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = darkBlue
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "No te pierdas los próximos eventos y reuniones importantes.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Copyright
                Text(
                    text = "© SaresTL 2024",
                    style = MaterialTheme.typography.bodySmall,
                    color = darkBlue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
            }
        }
    }
