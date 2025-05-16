package com.ekuipo.sarestl.models

data class NotificationResponse(
    val status: String,
    val message: String,
    val records: List<Notificacion>
)
