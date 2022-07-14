package io.github.keddnyo.midoze.local.dataModels

data class Request(
    val host: String,
    val region: String,
    val zeppVersion: String,
    val zeppLifeVersion: String
)