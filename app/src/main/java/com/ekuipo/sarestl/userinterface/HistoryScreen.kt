package com.ekuipo.sarestl.userinterface

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ekuipo.sarestl.R
import com.ekuipo.sarestl.models.HistoryRequest
import com.ekuipo.sarestl.models.HistoryResponse
import com.ekuipo.sarestl.models.Registro
import com.ekuipo.sarestl.models.SessionManager
import com.ekuipo.sarestl.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    // Definir los colores que coinciden con la interfaz web
    val lightBlue = Color(0xFF70A5F9)
    val darkBlue = Color(0xFF2D3748)
    val white = Color.White
    val brightBlue = Color(0xFF0D6EFD)

    // variable sesion
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val clave = sessionManager.getUserKey()


    val registros = remember { mutableStateOf<List<Registro>>(emptyList()) }

    var currentPage by remember { mutableStateOf(1) }
    val itemsPerPage = 8
    val totalPages = (registros.value.size + itemsPerPage - 1) / itemsPerPage

    val registroList = registros.value
    val startIndex = (currentPage - 1) * itemsPerPage
    val endIndex = minOf(startIndex + itemsPerPage, registroList.size)

    val currentRegistros = if (registroList.isNotEmpty() && startIndex < registroList.size)
        registroList.subList(startIndex, endIndex)
    else
        emptyList()

    LaunchedEffect(clave) {
        val getPersonalRegistration = HistoryRequest(clave)
        RetrofitClient.apiService.get_personal_registration(getPersonalRegistration)
            .enqueue(object: Callback<HistoryResponse>{
                override fun onResponse(
                    call: Call<HistoryResponse>,
                    response: Response<HistoryResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == "success"){
                        registros.value = (response.body()?.records ?: emptyList()) as List<Registro>
                    }else{
                        android.app.AlertDialog.Builder(context)
                            .setMessage("Hubo un error al traer los registros")
                            .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                            .setPositiveButton("Aceptar") { dialog, _ ->
                                dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                            }
                            .create()
                            .show()
                    }
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                    android.app.AlertDialog.Builder(context)
                        .setMessage("Error de conexion")
                        .setCancelable(false)  // No se puede cerrar tocando fuera del diálogo
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()  // Cerrar el diálogo después de presionar "Sí"
                        }
                        .create()
                        .show()
                }

            })
    }


    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("Pagina principal", "Notificaciones", "Credencial Digital", "Historial de Registros", "Mi Perfil", "Cerrar Sesión")

    val url = "https://hugoc.pythonanywhere.com/profile_pics/"

    var imageLoader = ImageLoader(context)

    LaunchedEffect(Unit) {
        // Limpiar caché de imágenes
        imageLoader.memoryCache?.clear()  // Limpiar la memoria
        imageLoader.diskCache?.clear()  // Limpiar el caché de disco
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
                                                "Cerrar Sesión" -> {
                                                    sessionManager.clearSession()
                                                    navController.navigate("login"){
                                                        popUpTo(0) {inclusive = true}
                                                    }
                                                }
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "HISTORIAL DE REGISTROS",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Tabla de registros
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = white),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        // Encabezado de la tabla
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF5F5F5))
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Usuario",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1.5f)
                            )
                            Text(
                                text = "Tipo",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Fecha/Hora",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }

                        Divider(color = Color.LightGray, thickness = 1.dp)

                        // Filas de datos
                        LazyColumn {
                            items(registros.value) { Registro ->
                                RegistroRow(Registro)
                                Divider()
                            }
                        }
                    }
                }

                // Controles de paginación
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (currentPage > 1) currentPage--
                        },
                        enabled = currentPage > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Anterior",
                            tint = if (currentPage > 1) brightBlue else Color.Gray
                        )
                    }

                    Text(
                        text = "$currentPage/$totalPages",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = {
                            if (currentPage < totalPages) currentPage++
                        },
                        enabled = currentPage < totalPages
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Siguiente",
                            tint = if (currentPage < totalPages) brightBlue else Color.Gray
                        )
                    }
                }

                // Botón Exportar
                Button(
                    onClick = { /* Sin funcionalidad */
                        createPdfWithTable (registros.value, context)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brightBlue),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Exportar",
                        color = white,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Copyright
                Text(
                    text = "© SaresTL 2024",
                    style = MaterialTheme.typography.bodySmall,
                    color = darkBlue,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun RegistroRow(registro: Registro) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Columna para Nombre y Clave
        Column(
            modifier = Modifier
                .weight(1.5f)
        ) {
            Text(
                text = registro.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = registro.clave,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // Columna para Tipo
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = registro.tipo,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = if (registro.tipo == "Entrada") Color(0xFF28A745) else Color(0xFFDC3545),
                textAlign = TextAlign.Center
            )
        }

        // Columna para Fecha/Hora
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = registro.fecha,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = formatTime(registro.hora),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

fun formatTime(seconds: String?): String {
    // Verificamos si la entrada es válida
    if (seconds.isNullOrEmpty()) return "00:00:00"  // Si es nulo o vacío, devolvemos una hora predeterminada.

    return try {
        // Convertimos el string a Double (ya que tiene un punto decimal)
        val doubleValue = seconds.toDouble()

        // Convertimos los segundos a milisegundos
        val date = Date((doubleValue * 1000).toLong())  // Multiplicamos por 1000 y lo convertimos a Long

        // Usamos un formato para mostrar la hora en HH:mm:ss
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        dateFormat.format(date)  // Devuelve la hora en formato HH:mm:ss
    } catch (e: NumberFormatException) {
        // En caso de error de conversión (si el string no es un número válido)
        "00:00:00"
    }
}
fun createPdfWithTable(registros: List<Registro>, context: Context) {
    // Crear un nuevo documento PDF
    val pdfDocument = PdfDocument()

    // Definir la página con orientación horizontal (landscape)
    val pageInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create() // Tamaño A4 en formato horizontal
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    val paint = Paint()

    // Cargar imágenes para la cabecera (ajusta el path según la ubicación)
    val logoSares = BitmapFactory.decodeResource(context.resources, R.drawable.sares)  // Logo de Sares
    val logoSep = BitmapFactory.decodeResource(context.resources, R.drawable.sep)      // Logo SEP
    val logoTecnm = BitmapFactory.decodeResource(context.resources, R.drawable.tecnm)  // Logo Tecnológico
    val logoTec = BitmapFactory.decodeResource(context.resources, R.drawable.tec)      // Logo Tec

    // Reducir el tamaño de los logos para la cabecera
    val logoSize = 80 // Tamaño reducido de los logos (un poco más grande que antes)

    // Escalar las imágenes para ajustarlas al tamaño especificado
    val scaledLogoSares = Bitmap.createScaledBitmap(logoSares, logoSize, logoSize, false)
    val scaledLogoSep = Bitmap.createScaledBitmap(logoSep, 120, logoSize, false)
    val scaledLogoTecnm = Bitmap.createScaledBitmap(logoTecnm, 120, logoSize, false)
    val scaledLogoTec = Bitmap.createScaledBitmap(logoTec, logoSize, logoSize, false)

    // Establecer el color del texto y tamaño
    paint.color = Color.Black.toArgb()
    paint.textSize = 14f

    // Dibujar los logos en la cabecera (alineados de forma más espaciosa)
    canvas.drawBitmap(scaledLogoSares, 50f, 30f, paint)
    canvas.drawBitmap(scaledLogoSep, 150f, 30f, paint)
    canvas.drawBitmap(scaledLogoTecnm, 290f, 30f, paint)
    canvas.drawBitmap(scaledLogoTec, 440f, 30f, paint)

    // Espacio entre los logos y el eslogan
    val spaceAfterLogos = 30f // Ajusta este valor para el espacio deseado entre los logos y el eslogan

    // Eslogan debajo de los logos
    paint.textSize = 18f
    paint.color = Color.Blue.toArgb()
    canvas.drawText("¡SaresTL, Construyendo un acceso seguro!", 50f, 120f + spaceAfterLogos, paint)

    // Establecer formato de tabla
    paint.textSize = 12f
    val columnWidthClave = 100f // Ancho de la columna "Clave"
    val columnWidthNombre = 180f // Ancho de la columna "Nombre"
    val columnWidthFecha = 120f // Ancho de la columna "Fecha"
    val columnWidthHora = 120f // Ancho de la columna "Hora"
    val columnWidthTipo = 100f // Ancho de la columna "Tipo"
    val columnWidthTipoUsuario = 120f // Ancho de la columna "Tipo Usuario"
    var yPosition = 150f + spaceAfterLogos // Posición inicial de la primera fila (con espacio añadido)

    // Dibujar cabecera de la tabla
    paint.color = Color.Black.toArgb()
    canvas.drawText("Clave", 50f, yPosition, paint)
    canvas.drawText("Nombre", 50f + columnWidthClave, yPosition, paint)
    canvas.drawText("Fecha", 50f + columnWidthClave + columnWidthNombre, yPosition, paint)
    canvas.drawText("Hora", 50f + columnWidthClave + columnWidthNombre + columnWidthFecha, yPosition, paint)
    canvas.drawText("Tipo", 50f + columnWidthClave + columnWidthNombre + columnWidthFecha + columnWidthHora, yPosition, paint)
    canvas.drawText("Tipo Usuario", 50f + columnWidthClave + columnWidthNombre + columnWidthFecha + columnWidthHora + columnWidthTipo, yPosition, paint)
    yPosition += 20f // Separación entre la cabecera y los registros

    // Dibujar los registros
    for (registro in registros) {
        canvas.drawText(registro.clave, 50f, yPosition, paint)

        // Si el nombre es demasiado largo, se divide en varias líneas
        val nameLines = splitTextToFit(registro.nombre, columnWidthNombre, paint, canvas)
        var currentYPosition = yPosition
        for (line in nameLines) {
            canvas.drawText(line, 50f + columnWidthClave, currentYPosition, paint)
            currentYPosition += 20f
        }

        canvas.drawText(registro.fecha, 50f + columnWidthClave + columnWidthNombre, currentYPosition, paint)
        canvas.drawText(formatTime(registro.hora), 50f + columnWidthClave + columnWidthNombre + columnWidthFecha, currentYPosition, paint)
        canvas.drawText(registro.tipo, 50f + columnWidthClave + columnWidthNombre + columnWidthFecha + columnWidthHora, currentYPosition, paint)
        canvas.drawText(registro.tipoUsuario, 50f + columnWidthClave + columnWidthNombre + columnWidthFecha + columnWidthHora + columnWidthTipo, currentYPosition, paint)

        yPosition = currentYPosition + 20f // Ajustamos la posición para el siguiente registro

        if (yPosition > 700) {
            // Si se alcanza el límite de la página, crear una nueva página
            pdfDocument.finishPage(page)
            val newPage = pdfDocument.startPage(pageInfo)
            canvas.drawText("Continuación...", 50f, 50f, paint) // Puedes personalizar este texto
            yPosition = 100f // Reseteamos la posición Y para la nueva página
        }
    }

    // Finalizar la página
    pdfDocument.finishPage(page)

    // Guardar el archivo PDF
    try {
        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "registros_con_imagenes_horizontal.pdf")
        val fileOutputStream = FileOutputStream(filePath)
        pdfDocument.writeTo(fileOutputStream)
        pdfDocument.close()

        // Abrir el archivo generado con un Intent
        val uri = FileProvider.getUriForFile(context, "com.tuapp.provider", filePath)
        val openIntent = Intent(Intent.ACTION_VIEW)
        openIntent.setDataAndType(uri, "application/pdf")
        openIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(openIntent)

        // Notificar al usuario que el archivo se ha generado
        Toast.makeText(context, "PDF generado exitosamente", Toast.LENGTH_SHORT).show()

    } catch (e: IOException) {
        e.printStackTrace()
    }
}

// Función para dividir el texto en varias líneas si excede el ancho de la columna
fun splitTextToFit(text: String, maxWidth: Float, paint: Paint, canvas: android.graphics.Canvas): List<String> {
    val lines = mutableListOf<String>()
    var currentLine = ""
    val words = text.split(" ")

    for (word in words) {
        val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
        val testWidth = paint.measureText(testLine)

        if (testWidth < maxWidth) {
            currentLine = testLine
        } else {
            lines.add(currentLine)
            currentLine = word
        }
    }

    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }

    return lines
}
