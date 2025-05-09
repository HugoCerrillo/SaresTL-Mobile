package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ekuipo.sarestl.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import com.ekuipo.sarestl.models.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfilen(navController: NavController) {
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val brightBlue = Color(0xFF0D6EFD)
    val gray = Color(0xFFADB5BD)

    // Estados para los campos del formulario
    var correo by remember { mutableStateOf("usuario@tecnm.mx") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("Pagina principal", "Notificaciones", "Credencial Digital", "Historial de Registros", "Mi Perfil", "Cerrar Sesion")

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val clave = sessionManager.getUserKey()



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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "ACTUALIZAR PERFIL",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Sección de datos de acceso
                Text(
                    text = "DATOS DE ACCESO",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 16.dp, top = 8.dp)
                )

                // Información de usuario (no editable)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Usuario",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    Text(
                        //text = "214890204",
                        text = "$clave",
                        style = MaterialTheme.typography.bodyLarge,
                        color = darkBlue
                    )
                }

                // Campo de correo electrónico
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Correo electrónico",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = brightBlue,
                            unfocusedContainerColor = white,
                            focusedContainerColor = white
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }

                // Campo de contraseña
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contraseña",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = brightBlue,
                            unfocusedContainerColor = white,
                            focusedContainerColor = white
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                // Campo de confirmar contraseña
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Confirmar Contraseña",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    OutlinedTextField(
                        value = confirmarContrasena,
                        onValueChange = { confirmarContrasena = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = brightBlue,
                            unfocusedContainerColor = white,
                            focusedContainerColor = white
                        ),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }

                // Sección de fotografía
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Fotografía",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clickable { /* Acción para seleccionar foto */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Añadir foto",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botones de acción
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Botón Cancelar
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = gray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            color = white,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Botón Guardar cambios
                    Button(
                        onClick = { /* Guardar cambios y navegar */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = brightBlue),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Guardar cambios",
                            color = white,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
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