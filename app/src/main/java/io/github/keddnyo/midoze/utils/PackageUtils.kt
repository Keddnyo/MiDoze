package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri

class PackageUtils(val context: Context, private val customPackageName: String) {
    private fun getPackageInfo(): PackageInfo? {
        return try {
            context.packageManager.getPackageInfo(customPackageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    fun getPackageVersionName() = getPackageInfo()?.versionName
    fun getPackageVersionBuild() = getPackageInfo()?.versionCode
    fun getPackageVersionNameAndBuild() = "${getPackageVersionName()}_${getPackageVersionBuild()}"

    fun removePackage() = with(context) {
        startActivity(
            Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:$customPackageName")
            }
        )
    }
}