package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ekuipo.sarestl.R
import com.ekuipo.sarestl.models.HistoryRequest
import com.ekuipo.sarestl.models.HistoryResponse
import com.ekuipo.sarestl.models.Notificacion
import com.ekuipo.sarestl.models.NotificationRequest
import com.ekuipo.sarestl.models.NotificationResponse
import com.ekuipo.sarestl.models.Registro
import com.ekuipo.sarestl.models.SessionManager
import com.ekuipo.sarestl.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val lightGray = Color(0xFFE9ECEF)

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val clave = sessionManager.getUserKey()
    val url = "https://hugoc.pythonanywhere.com/profile_pics/"

    // Datos de ejemplo para las notificaciones
    val notificaciones = remember { mutableStateOf<List<Notificacion>>(emptyList()) }

// Estado para la paginación
    var currentPage by remember { mutableStateOf(1) }
    val totalPages = 4
    val itemsPerPage = 8

// Acceder correctamente al tamaño de notificaciones
    val notificacionesList = notificaciones.value
    val startIndex = (currentPage - 1) * itemsPerPage
    val endIndex = minOf(startIndex + itemsPerPage, notificacionesList.size)

// Obtener las notificaciones de la página actual
    val currentNotificaciones = notificacionesList.subList(startIndex, endIndex)


    LaunchedEffect(clave) {
        val getNotificacion = NotificationRequest(clave)
        RetrofitClient.apiService.getNotifications(getNotificacion)
            .enqueue(object: Callback<NotificationResponse> {
                override fun onResponse(
                    call: Call<NotificationResponse>,
                    response: Response<NotificationResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == "success"){
                        notificaciones.value = (response.body()?.records ?: emptyList()) as List<Notificacion>
                    }else{
                        android.app.AlertDialog.Builder(context)
                            .setMessage("Hubo un error al obtener las notificaciones")
                            .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                            .setPositiveButton("Aceptar") { dialog, _ ->
                                dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                            }
                            .create()
                            .show()
                    }
                }

                override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                    android.app.AlertDialog.Builder(context)
                        .setMessage("Error de conexion")
                        .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                        }
                        .create()
                        .show()
                }
            })
    }



    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("Pagina principal", "Notificaciones", "Credencial Digital", "Historial de Registros", "Mi Perfil", "Cerrar Sesion")

    var imageLoader = ImageLoader(context)

    LaunchedEffect(Unit) {
        // Limpiar caché de imágenes
        imageLoader.memoryCache?.clear()  // Limpiar la memoria
        imageLoader.diskCache?.clear()  // Limpiar el caché de disco
    }


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
                            if (clave != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data("$url$clave.jpg")
                                        .diskCachePolicy(CachePolicy.DISABLED) // Deshabilitar caché
                                        .memoryCachePolicy(CachePolicy.DISABLED) // Deshabilitar caché en memoria
                                        .build(),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.perfil),
                                    contentDescription = "Perfil",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                )
                            }
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "NOTIFICACIONES",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Lista de notificaciones
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notificaciones.value) { Notificacion ->
                        NotificacionItem(Notificacion)
                        //Divider()
                    }
                }

                // Controles de paginación
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentPage > 1) currentPage--
                        },
                        enabled = currentPage > 1,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Anterior",
                            tint = if (currentPage > 1) darkBlue else Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(40.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$currentPage/$totalPages",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = darkBlue
                        )
                    }

                    IconButton(
                        onClick = {
                            if (currentPage < totalPages) currentPage++
                        },
                        enabled = currentPage < totalPages,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Siguiente",
                            tint = if (currentPage < totalPages) darkBlue else Color.Gray
                        )
                    }
                }

                // Copyright
                Text(
                    text = "© SaresTL 2025",
                    style = MaterialTheme.typography.bodySmall,
                    color = darkBlue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun NotificacionItem(notificacion: Notificacion) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE9ECEF)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Tipo de notificación
            Text(
                text = notificacion.mensaje,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            // Fecha y hora
            Text(
                text = "${notificacion.fechaNotificacion} - ${formatTime(notificacion.horaNotificacion)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            // Menú de opciones
            Box {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        tint = Color.Gray
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Ver detalles") },
                        onClick = { showMenu = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Marcar como leída") },
                        onClick = { showMenu = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = { showMenu = false }
                    )
                }
            }
        }
    }
}