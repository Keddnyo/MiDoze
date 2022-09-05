package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import io.github.keddnyo.midoze.local.packages.PackageNames
import io.github.keddnyo.midoze.local.packages.PackageVersions

object StringUtils {
    fun String.toServerResponse(): String {
        return this.replace("\\/", "/")
    }

    fun String.showAsToast(context: Context) =
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()

    fun Context.getPackageVersion(): String = PackageUtils(this, this.packageName).getPackageVersionName()

    fun String.getPackageVersion(context: Context): String? {
        return try {
            PackageUtils(context, this).getPackageVersionNameAndBuild()
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