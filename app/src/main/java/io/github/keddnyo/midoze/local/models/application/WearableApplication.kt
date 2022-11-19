package io.github.keddnyo.midoze.local.models.application

import androidx.annotation.DrawableRes

data class WearableApplication(
    @DrawableRes val icon: Int,
    val name: String,
    val packageName: String
)