package com.ekuipo.sarestl.models

data class EditProfileResponse(
    val username: String,
    val name: String,
    val clave: String,
    val correo: String,
    val password: String,
    val telefono: String,
    val imagen: String
)
