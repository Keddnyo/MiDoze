package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_LIFE_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageNames.ZEPP_PACKAGE_NAME
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_LIFE_VERSION
import io.github.keddnyo.midoze.local.packages.PackageVersions.ZEPP_VERSION

class PackageUtils(val context: Context) {
    fun getPackageVersion(packageName: String): String? = with(context) {
        return try {
            val eInfo = packageManager.getPackageInfo(
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

    fun removePackage(packageName: String) = with(context) {
        startActivity(
            Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:$packageName")
            }
        )
    }
}