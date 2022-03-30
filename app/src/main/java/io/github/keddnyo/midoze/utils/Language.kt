package io.github.keddnyo.midoze.utils

import java.util.*

class Language {
    private fun getCurrent(): String {
        return Locale.getDefault().language.toString()
    }

    fun getName(lang: String): String {
        val arrayOfLanguageCodes = lang.split(",").toTypedArray()
        val arrayOfLanguageNames = arrayListOf<String>()
        val currentLanguage = Locale(getCurrent())

        for (i in arrayOfLanguageCodes) {
            arrayOfLanguageNames.add(
                Locale(i).getDisplayName(currentLanguage)
            )
        }

        return arrayOfLanguageNames.toString()
            .substring(1,arrayOfLanguageNames.toString().length -1)
    }
}