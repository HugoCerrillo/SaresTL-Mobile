package com.ekuipo.sarestl.models

data class EditProfileResponse(
    val status: String,
    val message: String,
    val username: String,
    val phoneNumber: String,
    //podemos traer mas datos si lo necesitamos
)
