package com.ekuipo.sarestl.models

data class LoginResponse(
    val status: String,
    val message: String,
    val clave: String,
    val nombre: String,
    val rol: String,
)
