package com.ekuipo.sarestl.userinterface

import android.content.Context
import android.net.Uri
//import android.os.Bundle
import android.widget.Toast
//import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import coil.ImageLoader
//import coil.compose.rememberAsyncImagePainter
import com.ekuipo.sarestl.models.EditProfileRequest
//import com.ekuipo.sarestl.models.ResetPasswordResponse
import com.ekuipo.sarestl.models.SessionManager
import com.ekuipo.sarestl.network.RetrofitClient
import com.ekuipo.sarestl.network.subirImagen
//import dalvik.system.ZipPathValidator.Callback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.io.File
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ekuipo.sarestl.models.EditProfileResponse

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
    //var correo by remember { mutableStateOf("usuario@tecnm.mx") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("Pagina principal", "Notificaciones", "Credencial Digital", "Historial de Registros", "Mi Perfil", "Cerrar Sesion")

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val clave = sessionManager.getUserKey()
    var correo = sessionManager.getUserEmail();
    var telefono by remember { mutableStateOf(sessionManager.getUserPhone() ?: "") }
    val url = "https://hugoc.pythonanywhere.com/profile_pics/"

    //para la fotito
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    val scope = rememberCoroutineScope()

    var imageLoader = ImageLoader(context)

    LaunchedEffect(Unit) {
        // Limpiar caché de imágenes
        imageLoader.memoryCache?.clear()  // Limpiar la memoria
        imageLoader.diskCache?.clear()  // Limpiar el caché de disco
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenUri = uri
        uri?.let {
            scope.launch {
                val archivo = crearArchivoTemporalConNombre(context, it, "$clave.jpg")
                val subidaExitosa = subirImagen(archivo)

                if (subidaExitosa){
                    android.app.AlertDialog.Builder(context)
                        .setMessage("✅ Imagen subida correctamente.")
                        .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            navController.navigate("EditProfile")
                            dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                        }
                        .create()
                        .show()
                }else{
                    android.app.AlertDialog.Builder(context)
                        .setMessage("❌ Falló la subida, intentelo nuevamente mas tarde.")
                        .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            navController.navigate("EditProfile")
                            dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                        }
                        .create()
                        .show()
                }
            }
        }
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
                                //imageLoader = ImageLoader(context)

                                //imageLoader.memoryCache?.clear()  // Limpiar caché de memoria
                                //imageLoader.diskCache?.clear()    // Limpiar caché de disco

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
                                                "Cerrar Sesión" -> navController.navigate("login")
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
                        text = "Clave",
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
                        text = "Correo",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    Text (
                        text = "$correo",
                        style = MaterialTheme.typography.bodyLarge,
                        color = darkBlue,
                        //onValueChange = { correo = it },
                        //modifier = Modifier.fillMaxWidth(),
                        //shape = RoundedCornerShape(4.dp),
                        //colors = OutlinedTextFieldDefaults.colors(
                        //unfocusedBorderColor = Color.LightGray,
                        //focusedBorderColor = brightBlue,
                        //unfocusedContainerColor = white,
                        //focusedContainerColor = white
                        //),
                        //singleLine = true,
                        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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

                // telefono
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Teléfono",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = darkBlue,
                        modifier = Modifier.width(160.dp)
                    )

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = brightBlue,
                            unfocusedContainerColor = white,
                            focusedContainerColor = white
                        ),
                        singleLine = true,
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
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (url != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data("$url$clave.jpg")
                                    .diskCachePolicy(CachePolicy.DISABLED) // Deshabilitar caché
                                    .memoryCachePolicy(CachePolicy.DISABLED) // Deshabilitar caché en memoria
                                    .build(),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Añadir foto",
                                tint = Color.Gray,
                                modifier = Modifier.size(32.dp)
                            )
                        }
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
                        onClick = {
                        /* Guardar cambios y navegar */
                            if (contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty() && telefono.isNotEmpty()){
                                if (contrasena == confirmarContrasena){
                                    val editProfileRequest = EditProfileRequest(clave, telefono, contrasena)
                                    RetrofitClient.apiService.setEditProfile(editProfileRequest)
                                        .enqueue(object: retrofit2.Callback<EditProfileResponse>{
                                            override fun onResponse(
                                                call: Call<EditProfileResponse>,
                                                response: Response<EditProfileResponse>
                                            ) {
                                                if (response.isSuccessful && response.body()?.status == "success"){
                                                    sessionManager.saveUserPhone(telefono)
                                                    //todo esta very gus
                                                    //mensaje de exito
                                                    android.app.AlertDialog.Builder(context)
                                                        .setMessage("Cambios realizados correctamente")
                                                        .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                        .setPositiveButton("Aceptar") { dialog, _ ->
                                                            navController.navigate("EditProfile")
                                                            dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                        }
                                                        .create()
                                                        .show()
                                                }else{
                                                    android.app.AlertDialog.Builder(context)
                                                        .setMessage("Ha ocurrido un error al cambiar los datos, intentelo nuevamente: " + response.body()?.status)
                                                        .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                        .setPositiveButton("Aceptar") { dialog, _ ->
                                                            navController.navigate("EditProfile")
                                                            dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                        }
                                                        .create()
                                                        .show()
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<EditProfileResponse>,
                                                t: Throwable
                                            ) {
                                                TODO("Not yet implemented")
                                                android.app.AlertDialog.Builder(context)
                                                    .setMessage("Error de conexión: " + t.toString())
                                                    .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                                                    .setPositiveButton("Aceptar") { dialog, _ ->
                                                        navController.navigate("EditProfile")
                                                        dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                                                    }
                                                    .create()
                                                    .show()
                                            }

                                        })
                                }
                            }
                        },
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



fun crearArchivoTemporalConNombre(context: Context, uri: Uri, nombre: String): File {
    val inputStream = context.contentResolver.openInputStream(uri)!!
    val archivoTemporal = File(context.cacheDir, nombre)

    inputStream.use { input ->
        archivoTemporal.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return archivoTemporal
}