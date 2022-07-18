package io.github.keddnyo.midoze.utils

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast
import java.util.*

class Display {
    fun getGridLayoutIndex(
        context: Context,
        columnWidthDp: Int,
    ): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp.toString().toFloat() + 0.5).toInt()
    }

    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun getLanguageName(lang: String): String {
        val arrayOfLanguageCodes = lang.split(",").toTypedArray()
        val arrayOfLanguageNames = arrayListOf<String>()
        val currentLanguage = Locale(LanguageUtils().currentLocale.language.toString())

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