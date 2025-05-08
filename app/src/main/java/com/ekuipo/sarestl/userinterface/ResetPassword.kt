package com.ekuipo.sarestl.userinterface

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.ekuipo.sarestl.models.LoginRequest
import com.ekuipo.sarestl.models.LoginResponse
import com.ekuipo.sarestl.models.ResetPasswordRequest
import com.ekuipo.sarestl.models.ResetPasswordResponse
import com.ekuipo.sarestl.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(navController: NavController) {
    val context = LocalContext.current
    // Definir los colores que coinciden con la interfaz
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val buttonBlue = Color(0xFF1E88E5)
    val linkBlue = Color(0xFF2196F3)
    val white = Color.White

    // Estado para el campo de correo electrónico
    var correo by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBlue)
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(320.dp)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = white),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logos en fila
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sep),
                        contentDescription = "Logo SEP",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.tecnm),
                        contentDescription = "Logo Institución",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.tec),
                        contentDescription = "Logo itl",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.sares),
                        contentDescription = "Logo sares",
                        modifier = Modifier.size(40.dp)
                    )
                }
                // contenido
                // Título
                Text(
                    text = "Restablecer Contraseña",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Etiqueta de correo electrónico
                Text(
                    text = "Correo electrónico",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                // Campo de correo electrónico
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    placeholder = { Text("Ingresa tu correo electrónico") },
                    shape = RoundedCornerShape(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = buttonBlue,
                        unfocusedContainerColor = white,
                        focusedContainerColor = white
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // Botón Recuperar Contraseña
                Button(
                    onClick = {
                    /* Acción para recuperar contraseña */
                        if (correo.isNotEmpty()){
                            val resetPasswordRequest = ResetPasswordRequest(correo)
                            RetrofitClient.apiService.resetPassword(resetPasswordRequest)
                                .enqueue(object : Callback<ResetPasswordResponse>{
                                    override fun onResponse (
                                        call: Call<ResetPasswordResponse>,
                                        response: Response<ResetPasswordResponse>
                                    ){
                                        if (response.isSuccessful && response.body()?.status == "success"){
                                            android.app.AlertDialog.Builder(context)
                                                .setMessage("¡Se ha enviado un enlace para restablecer tu contraseña, verifica tu correo electronico (tambien en spam)!")
                                                .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                .setPositiveButton("Aceptar") { dialog, _ ->
                                                    navController.navigate("login")
                                                    dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                }
                                                .create()
                                                .show()
                                        }else{
                                            android.app.AlertDialog.Builder(context)
                                                .setMessage("El correo electronico ingresado no coincide con ningun Usuario de SaresTL, intente nuevamente con un correo distinto")
                                                .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                .setPositiveButton("Aceptar") { dialog, _ ->
                                                    dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                }
                                                .create()
                                                .show()
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<ResetPasswordResponse>,
                                        t: Throwable
                                    ) {
                                        android.app.AlertDialog.Builder(context)
                                            .setMessage("Ha ocurrido un error al contactar con el servidor, intentelo de nuevo mas tarde")
                                            .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                            .setPositiveButton("Aceptar") { dialog, _ ->
                                                dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                            }
                                            .create()
                                            .show()
                                    }
                                })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBlue),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Recuperar Contraseña",
                        color = white,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Enlace Regresar al menú
                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Regresar al menu",
                        color = linkBlue,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                //Spacer(modifier = Modifier.weight(1f))

                // Copyright
                Text(
                    text = "© 2024 SARES TL",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}