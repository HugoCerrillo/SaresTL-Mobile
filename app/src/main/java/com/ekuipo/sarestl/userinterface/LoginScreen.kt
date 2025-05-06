package com.ekuipo.sarestl.userinterface

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ekuipo.sarestl.R
import com.ekuipo.sarestl.models.LoginRequest
import com.ekuipo.sarestl.models.LoginResponse
import com.ekuipo.sarestl.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    // Definir los colores que coinciden con la interfaz web
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val selectedTabColor = Color(0xFF0D6EFD)
    val unselectedTabColor = Color.Gray

    // Estados para los campos de texto
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Estado para la pestaña seleccionada
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Iniciar Sesión", "Manual de usuario", "Citas")


    //olvide contraeseña
    // Definir los colores que coinciden con la interfaz
    val buttonBlue = Color(0xFF1E88E5)
    val linkBlue = Color(0xFF2196F3)


    // Estado para el campo de correo electrónico
    var correo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            // Barra superior con logos institucionales (estilo dashboard)
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = white),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
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
                }


                // Menú de navegación con pestañas
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    containerColor = white,
                    contentColor = selectedTabColor,
                    indicator = { tabPositions ->
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTab])
                                .height(3.dp)
                                .background(
                                    color = selectedTabColor,
                                    shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
                                )
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                                when (index) {
                                    0 -> {} // Ya estamos en login
                                    1 -> navController.navigate("UserManual")
                                    2 -> navController.navigate("Citas")
                                }
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTab == index) selectedTabColor else unselectedTabColor
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Contenido principal (tarjeta de login)
            if (selectedTab == 0) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(),
                    colors = CardDefaults.cardColors(containerColor = white),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Título
                        Text(
                            text = "¡SaresTL, Construyendo un acceso seguro!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.sares),
                            contentDescription = "Logo SaresTL",
                            modifier = Modifier.height(150.dp)
                        )

                        // Campo de usuario
                        OutlinedTextField(
                            label = { Text(text = "Usuario") },
                            maxLines = 1,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            value = username,
                            onValueChange = { username = it },
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo de contraseña
                        OutlinedTextField(
                            label = { Text(text = "Contraseña") },
                            maxLines = 1,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = { password = it },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón de inicio de sesión
                        Button(
                            onClick = {
                                if (username.isNotEmpty() && password.isNotEmpty()) {
                                    isLoading = true
                                    val loginRequest = LoginRequest(username, password)
                                    RetrofitClient.apiService.login(loginRequest)
                                        .enqueue(object : Callback<LoginResponse> {
                                            override fun onResponse(
                                                call: Call<LoginResponse>,
                                                response: Response<LoginResponse>
                                            ) {
                                                isLoading = false
                                                if (response.isSuccessful && response.body()?.status == "success") {
                                                    navController.navigate("home")  // Navegar a Home si el login es exitoso
                                                } else {
                                                    Toast.makeText(
                                                        navController.context,
                                                        "Credenciales incorrectas",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<LoginResponse>,
                                                t: Throwable
                                            ) {
                                                isLoading = false
                                                Toast.makeText(
                                                    navController.context,
                                                    "Error de conexión",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        })
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = darkBlue)
                        ) {
                            Text(
                                text = if (isLoading) "Cargando..." else "Iniciar Sesión",
                                color = white,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Enlaces adicionales
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onClick = { navController.navigate("UserType") }
                            ) {
                                Text(
                                    text = "Registrarse",
                                    color = Color(0xFF0000FF),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            TextButton(
                                onClick = { navController.navigate("ResetPassword") }
                            ) {
                                Text(
                                    text = "¿Olvidaste tu contraseña?",
                                    color = Color(0xFF0000FF),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
            // Aquí se mostrarían los otros contenidos según la pestaña seleccionada
            // (Manual de usuario o Citas)

            Spacer(modifier = Modifier.height(25.dp))
            // Copyright
            Text(
                text = "© SaresTL 2024",
                style = MaterialTheme.typography.bodySmall,
                color = darkBlue,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}