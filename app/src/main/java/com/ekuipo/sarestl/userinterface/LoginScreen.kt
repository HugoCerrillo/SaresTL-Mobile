package com.ekuipo.sarestl.userinterface

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.ekuipo.sarestl.R
import com.ekuipo.sarestl.models.LoginRequest
import com.ekuipo.sarestl.models.LoginResponse
import com.ekuipo.sarestl.models.SessionManager
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

    // Scroll state para permitir desplazamiento
    val scrollState = rememberScrollState()

    // Seession Manager(context)
    val context = LocalContext.current
    val sessionManager = SessionManager(context)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // Añadido scroll vertical aquí
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Barra superior con logos institucionales (estilo dashboard)
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = white),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column { // Cambiado a Column para mejor organización
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
                            .fillMaxWidth(),
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
                                        //1 -> navController.navigate("UserManual")
                                        //2 -> navController.navigate("Citas")
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
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Contenido principal (tarjeta de login)
            if (selectedTab == 0) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        Spacer(modifier = Modifier.height(20.dp))

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
                                                // aqui lo del seesiion con la vaiable

                                                isLoading = false
                                                if (response.isSuccessful && response.body()?.status == "success") {
                                                    val clave = response.body()?.clave
                                                    val name = response.body()?.name
                                                    val userType = response.body()?.userType
                                                    val correo = response.body()?.correo
                                                    val telefono = response.body()?.telefono
                                                    val imagen = response.body()?.imagen


                                                    if (clave != null) {
                                                        sessionManager.saveIsLogged(true)
                                                        val isLogged = sessionManager.getIsLogged()
                                                        Log.d("MyTag", "The value of isLogged is: $isLogged")
                                                        sessionManager.saveUserKey(clave)
                                                        sessionManager.saveUserName(name)
                                                        sessionManager.saveUserRol(userType)
                                                        sessionManager.saveUserEmail(correo)
                                                        sessionManager.saveUserPhone(telefono)
                                                        sessionManager.saveUserImage(imagen)

                                                        navController.navigate("home"){
                                                            popUpTo("login") { inclusive = true }
                                                        }
                                                    }
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
            } else if (selectedTab == 1) {
                // ID del video de YouTube
                val videoId = "YclUefDEZBI"

                // Barra superior con logos institucionales (estilo dashboard)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = white),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {


                    // Contenido principal (Manual de usuario)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                                text = "Manual de usuario",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )

                            // Reproductor de YouTube
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f)
                                    .background(Color.Black)
                            ) {
                                AndroidView(
                                    factory = { context ->
                                        WebView(context).apply {
                                            layoutParams = ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.MATCH_PARENT
                                            )
                                            webViewClient = WebViewClient()
                                            settings.javaScriptEnabled = true
                                            settings.loadWithOverviewMode = true
                                            settings.useWideViewPort = true

                                            // Cargar el video de YouTube
                                            loadData(
                                                """
                                <html>
                                    <body style="margin:0;padding:0">
                                        <iframe 
                                            width="100%" 
                                            height="100%" 
                                            src="https://www.youtube.com/embed/$videoId" 
                                            frameborder="0" 
                                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                            allowfullscreen>
                                        </iframe>
                                    </body>
                                </html>
                                """,
                                                "text/html",
                                                "utf-8"
                                            )
                                        }
                                    },
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Información adicional (opcional)
                            Text(
                                text = "Este video muestra cómo utilizar todas las funciones de la aplicación SaresTL.",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Enlaces adicionales
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(
                                    onClick = { /* Acción para descargar PDF */ }
                                ) {
                                    Text(
                                        text = "Descargar manual en PDF",
                                        color = linkBlue,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Copyright
                    Text(
                        text = "© SaresTL 2024",
                        style = MaterialTheme.typography.bodySmall,
                        color = darkBlue,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Copyright
                Text(
                    text = "© SaresTL 2024",
                    style = MaterialTheme.typography.bodySmall,
                    color = darkBlue,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }else if (selectedTab == 2){
                // Referencia al contexto actual
                val context = LocalContext.current

                // URL a abrir
                val citasUrl = "https://hugocerrillo.github.io/SaresTL/html/Modulo-OlvidarContrasena.html"

                // Estado para controlar si ya se ha abierto el navegador
                var navegadorAbierto by remember { mutableStateOf(false) }

                // Función para abrir la URL en el navegador
                val abrirNavegador: () -> Unit = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(citasUrl))
                        context.startActivity(intent)
                        navegadorAbierto = true
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "No se pudo abrir el navegador",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // Efecto para abrir el navegador automáticamente cuando se selecciona la pestaña
                LaunchedEffect(selectedTab) {
                    if (selectedTab == 2 && !navegadorAbierto) {
                        abrirNavegador()
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                            text = "Agenda tu cita para acceder al ITL",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        // Imagen ilustrativa
                        Image(
                            painter = painterResource(id = R.drawable.sares),
                            contentDescription = "Logo SaresTL",
                            modifier = Modifier.height(100.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Mensaje informativo
                        Text(
                            text = "Estás siendo redirigido al sistema de citas en línea...",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Indicador de carga
                        CircularProgressIndicator(
                            color = selectedTabColor,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón alternativo por si falla la redirección automática
                        Button(
                            onClick = abrirNavegador,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = darkBlue)
                        ) {
                            Text(
                                text = "Abrir sistema de citas",
                                color = white,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Información adicional
                        Text(
                            text = "Si no se abre automáticamente, presiona el botón de arriba.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

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
}