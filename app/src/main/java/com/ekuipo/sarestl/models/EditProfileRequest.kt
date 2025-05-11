package com.ekuipo.sarestl.models

data class EditProfileRequest(
    val username: String,
    val correo: String,
    val password: String,
    val telefono: String,
    val imagen: String
)
