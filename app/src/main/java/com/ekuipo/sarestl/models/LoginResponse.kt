package com.ekuipo.sarestl.models

data class LoginResponse(
    val status: String,
    val message: String,
    val clave: String,
    val name: String,
    val userType: String,
    val correo: String,
    val telefono: String,
    val imagen: String,
)
