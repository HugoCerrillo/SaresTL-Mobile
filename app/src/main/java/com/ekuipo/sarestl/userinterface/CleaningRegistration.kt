package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ekuipo.sarestl.R
import com.ekuipo.sarestl.models.RegisterRequest
import com.ekuipo.sarestl.models.RegisterResponse
import com.ekuipo.sarestl.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CleaningRegistration(navController: NavController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val black = Color.Black

    // Estados para los campos del formulario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    //var semestre by remember { mutableStateOf("") }
    var empresa by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val generos = listOf("Masculino", "Femenino")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = white),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
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
                    text = "Registro de Intendencia",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
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

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Nombre
                Text(
                    text = "Nombre:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                // Campo Correo Institucional
                Text(
                    text = "Correo Institucional:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // Campo Departamento
                Text(
                    text = "Departamento:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                // Campo Departamento
                Text(
                    text = "Empresa: ",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = empresa,
                    onValueChange = { empresa = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )


                // Campo Teléfono
                Text(
                    text = "Teléfono:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                // Campo Género (Dropdown)
                Text(
                    text = "Género:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    OutlinedTextField(
                        value = genero.ifEmpty { "Seleccione una opción" },
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
                        generos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(text = opcion) },
                                onClick = {
                                    genero = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Campo Fecha de Nacimiento
                Text(
                    text = "Fecha de Nacimiento:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    placeholder = { Text("aaaa-mm-dd") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Campo Usuario
                Text(
                    text = "Usuario:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                // Campos de Contraseña en fila
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        Text(
                            text = "Contraseña:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = contrasena,
                            onValueChange = { contrasena = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        Text(
                            text = "Confirmar Contraseña:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = confirmarContrasena,
                            onValueChange = { confirmarContrasena = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                }

                // Botón Crear Cuenta
                Button(
                    onClick = { /* Sin funcionalidad */
                        //codigo para mandar llamar a la APÏ
                        if (nombre.isNotEmpty() && correo.isNotEmpty() && department.isNotEmpty() && telefono.isNotEmpty() &&genero.isNotEmpty()&& fechaNacimiento.isNotEmpty() && usuario.isNotEmpty() && contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()){
                            if (contrasena == confirmarContrasena){
                                isLoading = true
                                val userType: String = "Intendente"
                                val semester: String = "nulo"
                                val registerRequest = RegisterRequest(nombre, correo, department, semester, telefono, genero, fechaNacimiento, usuario, contrasena, userType, empresa)
                                RetrofitClient.apiService.register(registerRequest)
                                    .enqueue(object : Callback<RegisterResponse> {
                                        override fun onResponse(
                                            call: Call<RegisterResponse>,
                                            response: Response<RegisterResponse>
                                        ) {
                                            isLoading = false
                                            if (response.isSuccessful && response.body()?.status == "success"){
                                                //navController.navigate("login")
                                                android.app.AlertDialog.Builder(context)
                                                    .setMessage("¡Te has registrado correctamente en el sistema!")
                                                    .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                    .setPositiveButton("Aceptar") { dialog, _ ->
                                                        navController.navigate("login")  // Navegar al destino 'login'
                                                        dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                    }
                                                    .create()
                                                    .show()
                                            }else{
                                                isLoading = false
                                                android.app.AlertDialog.Builder(context)
                                                    .setMessage("Ha ocurrido un error: ${response.body()?.message ?: "Mensaje no disponible"}")
                                                    .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                    .setPositiveButton("Aceptar") { dialog, _ ->
                                                        navController.navigate("CleaningRegistration")
                                                        dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                    }
                                                    .create()
                                                    .show()
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<RegisterResponse>,
                                            t: Throwable
                                        ) {
                                            isLoading = false
                                            android.app.AlertDialog.Builder(context)
                                                .setMessage("Error de conexión: " + t.toString())
                                                .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                .setPositiveButton("Aceptar") { dialog, _ ->
                                                    navController.navigate("CleaningRegistration")
                                                    dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                }
                                                .create()
                                                .show()
                                        }


                                    })
                            }else{
                                //las constraseñas no coinciden
                                isLoading = false
                                android.app.AlertDialog.Builder(context)
                                    .setMessage("Error: Las contraseñas no coinciden.")
                                    .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                    .setPositiveButton("Aceptar") { dialog, _ ->
                                        dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                    }
                                    .create()
                                    .show()
                            }

                        }else{
                            android.app.AlertDialog.Builder(context)
                                .setMessage("Error: Todos los campos deben ser llenados.")
                                .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                .setPositiveButton("Aceptar") { dialog, _ ->
                                    dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                }
                                .create()
                                .show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = black),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Crear Cuenta",
                        color = white,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Enlaces en la parte inferior
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = {
                    /* Sin funcionalidad */
                        navController.navigate("login")
                    }) {
                        Text(
                            text = "Iniciar Sesión",
                            color = Color(0xFF0000FF),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    TextButton(onClick = { navController.navigate("UserManual") }) {
                        Text(
                            text = "Manual Usuario",
                            color = Color(0xFF0000FF),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}