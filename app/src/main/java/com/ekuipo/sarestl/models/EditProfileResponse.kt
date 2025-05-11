package com.ekuipo.sarestl.models

data class EditProfileResponse(
    val username: String,
    val name: String,
    val phoneNumber: String,
    //podemos traer mas datos si lo necesitamos
)
