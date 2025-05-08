package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ekuipo.sarestl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserType(navController: NavController) {
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val black = Color.Black
    val purple = Color(0xFF5626C4)

    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    var tipoUsuario by remember { mutableStateOf("") }
    val tiposUsuario = listOf("Estudiante", "Docente", "Administrador", "Administrativo", "Intendente", "Guardia")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = white),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logos en fila
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sep),
                        contentDescription = "Logo SEP",
                        modifier = Modifier.height(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.tecnm),
                        contentDescription = "Logo TECNM",
                        modifier = Modifier.height(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.tec),
                        contentDescription = "Logo Campus",
                        modifier = Modifier.height(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.sares),
                        contentDescription = "Logo SaresTL",
                        modifier = Modifier.height(30.dp)
                    )
                }

                // Título
                Text(
                    text = "¡SARESTL, Construyendo un acceso seguro!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Icono de perfil
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Perfil",
                        modifier = Modifier.size(40.dp),
                        tint = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Etiqueta Tipo de Usuario
                Text(
                    text = "Tipo de Usuario:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                // Menú desplegable
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    OutlinedTextField(
                        value = tipoUsuario.ifEmpty { "Seleccione una opción" },
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        tiposUsuario.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(text = tipo) },
                                onClick = {
                                    tipoUsuario = tipo
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Botón Continuar
                Button(
                    onClick = {
                        when (tipoUsuario) {
                            "Estudiante" -> navController.navigate("StudentRegistration")
                            "Docente" -> navController.navigate("TeachingRegistration")
                            "Administrador" -> navController.navigate("AdministratorRegistration")
                            "Administrativo" -> navController.navigate("AdministrativeRegistration")
                            "Intendente" -> navController.navigate("CleaningRegistration")
                            "Guardia" -> navController.navigate("GuardRegistratio")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = black),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Continuar",
                        color = white,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Enlaces en la parte inferior
                TextButton(
                    onClick = {navController.navigate("login") },
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        color = purple,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }

                TextButton(
                    onClick = { navController.navigate("UserManual") },
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Manual de Usuario",
                        color = purple,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}