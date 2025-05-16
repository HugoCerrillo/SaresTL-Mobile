package com.ekuipo.sarestl.models

import com.google.gson.annotations.SerializedName

data class Registro(
    @SerializedName("idRegistroAcceso") val idRegistroAcceso: Int,
    @SerializedName("clave") val clave: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("fechaR") val fecha: String,
    @SerializedName("horaR") val hora: String,
    @SerializedName("tipoR") val tipo: String,
    @SerializedName("tipoUsuario") val tipoUsuario: String,
    @SerializedName("idMiembroInstitucion") val idMiembroInstitucion: Int
)