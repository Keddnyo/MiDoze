package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class PackageUtils(val context: Context) {
    fun removePackage(packageName: String) = with(context) {
        startActivity(
            Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:$packageName")
            }
        )
    }
}