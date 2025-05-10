package com.ekuipo.sarestl.models

data class EditProfileRequest(
    val username: String,
    val email: String,
    val password: String,
    //faltaria lo de la foto
)
