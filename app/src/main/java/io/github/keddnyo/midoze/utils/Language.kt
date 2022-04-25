package io.github.keddnyo.midoze.utils

import java.util.*

class Language {
    fun getCurrent(): Locale {
        return Locale.getDefault()
    }

    fun getName(lang: String): String {
        val arrayOfLanguageCodes = lang.split(",").toTypedArray()
        val arrayOfLanguageNames = arrayListOf<String>()
        val currentLanguage = Locale(getCurrent().language.toString())

        for (i in arrayOfLanguageCodes) {
            arrayOfLanguageNames.add(
                Locale(i).getDisplayName(currentLanguage)
            )
        }

        return arrayOfLanguageNames.toString()
            .substring(1, arrayOfLanguageNames.toString().length - 1)
            .replace(", pt-br", "")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } +
                "."
    }
}