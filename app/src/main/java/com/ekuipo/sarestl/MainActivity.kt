package com.ekuipo.sarestl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ekuipo.sarestl.ui.theme.SaresTLTheme
import com.ekuipo.sarestl.userinterface.DashboardScreen
import com.ekuipo.sarestl.userinterface.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SaresTLTheme {
                //main content
                // Asegúrate de tener un NavController para la navegación
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController)  // Pantalla de login
                    }
                    composable("home") {
                        DashboardScreen()  // Pantalla de home
                    }
                }
            }
        }
    }
}
