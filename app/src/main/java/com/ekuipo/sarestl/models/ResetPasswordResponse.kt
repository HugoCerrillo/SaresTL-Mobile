package com.ekuipo.sarestl.models

import android.os.Message

data class ResetPasswordResponse(
    val status: String,
    val message: String,
    val userMail: String,
    val userId: String
)
