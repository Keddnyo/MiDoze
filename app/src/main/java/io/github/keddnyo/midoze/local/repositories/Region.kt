package io.github.keddnyo.midoze.local.repositories

import io.github.keddnyo.midoze.utils.DozeLocale

open class Region (
    val country: String,
    val language: String,
) {
    object Default : Region(
        country = DozeLocale().currentCountry,
        language = DozeLocale().currentLanguage,
    )

    object Chinese : Region(
        country = "CH",
        language = "zh_CH",
    )
}