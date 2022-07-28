package io.github.keddnyo.midoze.utils

import android.content.Context

class AppVersion(val context: Context) {
    private val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val name: String = packageInfo.versionName
    val code: Int  = packageInfo.versionCode
}