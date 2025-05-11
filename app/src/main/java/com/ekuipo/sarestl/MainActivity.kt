package com.ekuipo.sarestl

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ekuipo.sarestl.ui.theme.SaresTLTheme
import com.ekuipo.sarestl.userinterface.AdministrativeRegistration
import com.ekuipo.sarestl.userinterface.AdministratorRegistration
import com.ekuipo.sarestl.userinterface.CleaningRegistration
import com.ekuipo.sarestl.userinterface.DashboardScreen
import com.ekuipo.sarestl.userinterface.DigitalCredential
import com.ekuipo.sarestl.userinterface.EditProfilen
import com.ekuipo.sarestl.userinterface.GuardRegistration
import com.ekuipo.sarestl.userinterface.HistoryScreen
import com.ekuipo.sarestl.userinterface.LoginScreen
import com.ekuipo.sarestl.userinterface.NotificationScreen
import com.ekuipo.sarestl.userinterface.ResetPassword
import com.ekuipo.sarestl.userinterface.StudentRegistration
import com.ekuipo.sarestl.userinterface.TeachingRegistration
//import com.ekuipo.sarestl.userinterface.UserManual
import com.ekuipo.sarestl.userinterface.UserType
import com.ekuipo.sarestl.models.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sessionManager = SessionManager(this)
        val isLogged = sessionManager.getIsLogged()
        Log.d("MyTag", "The value of isLogged is: $isLogged")
        setContent {
            SaresTLTheme {
                //main content
                // Asegúrate de tener un NavController para la navegación
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = if (isLogged) "home" else "login"){

                    composable("login") {
                        if(isLogged){

                        }else{
                            LoginScreen(navController)
                        }
                    }
                    composable("home") {
                        DashboardScreen(navController)  // Pantalla de home
                    }
                    composable("UserType") {
                        UserType(navController)
                    }
                    /*composable("UserManual") {
                        UserManual(navController)
                    }*/
                    composable("StudentRegistration") {
                        StudentRegistration(navController)
                    }
                    composable("TeachingRegistration") {
                        TeachingRegistration(navController)
                    }
                    composable("AdministratorRegistration") {
                        AdministratorRegistration(navController)
                    }
                    composable("AdministrativeRegistration") {
                        AdministrativeRegistration(navController)
                    }
                    composable("CleaningRegistration") {
                        CleaningRegistration(navController)
                    }
                    composable("GuardRegistration") {
                        GuardRegistration(navController)
                    }
                    composable("HistoryScreen") {
                        HistoryScreen(navController)
                    }
                    composable("EditProfile") {
                        EditProfilen(navController)
                    }
                    composable("NotificationScreen") {
                        NotificationScreen(navController)
                    }
                    composable("DigitalCredential") {
                        DigitalCredential(navController)
                    }
                    composable("ResetPassword") {
                        ResetPassword(navController)
                    }

                }
            }
        }
    }
}
