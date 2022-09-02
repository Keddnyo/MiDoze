package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

class PackageUtils(val context: Context, private val packageName: String) {
    private val packageInfo = context.packageManager.getPackageInfo(packageName, 0)

    fun getPackageVersionName(): String = packageInfo.versionName

    fun getPackageVersionBuild() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode.toInt()
    } else {
        packageInfo.versionCode
    }

    fun getPackageVersionNameAndBuild() = getPackageVersionName() + "_" + getPackageVersionBuild()

    fun removePackage() {
        context.startActivity(
            Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:$packageName")
            }
        )
    }
}