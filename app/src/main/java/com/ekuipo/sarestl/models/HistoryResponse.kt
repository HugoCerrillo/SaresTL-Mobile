package com.ekuipo.sarestl.models

data class HistoryResponse (
    val status: String,
    val message: String,
    val records: List<Registro>
)