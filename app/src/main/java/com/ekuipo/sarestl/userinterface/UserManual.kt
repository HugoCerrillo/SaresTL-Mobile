package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.ekuipo.sarestl.R


@Composable
fun UserManual(navController: NavController) {
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val selectedTabColor = Color(0xFF0D6EFD)
    val unselectedTabColor = Color.Gray
    val linkBlue = Color(0xFF3B82F6)

    // ID del video de YouTube
    val videoId = "YclUefDEZBI"

    // Estado para la pestaña seleccionada
    var selectedTab by remember { mutableStateOf(1) } // 1 para "Manual de usuario"
    val tabs = listOf("Iniciar Sesión", "Manual de usuario", "Citas")

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
                                0 -> navController.navigate("login")
                                1 -> {} // Ya estamos en Manual de usuario
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

            Spacer(modifier = Modifier.height(24.dp))

            // Contenido principal (Manual de usuario)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                            .aspectRatio(16f/9f)
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
    }
}