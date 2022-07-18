package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.pm.PackageManager
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_LIFE_VERSION
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_VERSION

class PackageUtils {
    fun getPackageVersion(context: Context, packageName: String): String? {
        return try {
            val eInfo = context.packageManager.getPackageInfo(
                packageName, 0
            )
            val version = eInfo.versionName
            val build = eInfo.versionCode
            version + "_" + build
        } catch (e: PackageManager.NameNotFoundException) {
            when (packageName) {
                ZEPP_PACKAGE_NAME -> {
                    ZEPP_VERSION
                }
                ZEPP_LIFE_PACKAGE_NAME -> {
                    ZEPP_LIFE_VERSION
                }
                else -> {
                    null
                }
            }
        }
    }
}