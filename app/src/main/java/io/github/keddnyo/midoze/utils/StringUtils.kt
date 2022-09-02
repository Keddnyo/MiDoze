package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import io.github.keddnyo.midoze.local.packages.PackageNames
import io.github.keddnyo.midoze.local.packages.PackageVersions
import java.util.*

object StringUtils {
    fun String.toLanguageList(): String {
        val arrayOfLanguageCodes = this.split(",").toTypedArray()
        val arrayOfLanguageNames = arrayListOf<String>()
        val currentLanguage = Locale(DozeLocale().currentLanguage)

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

    fun String.toServerResponse(): String {
        return this.replace("\\/", "/")
    }

    fun String.showAsToast(context: Context) =
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

    fun String.getPackageVersion(context: Context): String? {
        return try {
            PackageUtils(context, context.packageName).getPackageVersionNameAndBuild()
        } catch (e: PackageManager.NameNotFoundException) {
            when (this@getPackageVersion) {
                PackageNames.ZEPP_PACKAGE_NAME -> {
                    PackageVersions.ZEPP_VERSION
                }
                PackageNames.ZEPP_LIFE_PACKAGE_NAME -> {
                    PackageVersions.ZEPP_LIFE_VERSION
                }
                else -> {
                    null
                }
            }
        }
    }
}