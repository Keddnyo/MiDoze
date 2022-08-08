package io.github.keddnyo.midoze.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class Permissions(val context: Context) {
    private val writeExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    fun isWriteExternalStoragePermissionAvailable(): Boolean {
        ActivityCompat.checkSelfPermission(
            context,
            writeExternalStoragePermission
        ).let { permission ->
            return permission == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestWriteExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(writeExternalStoragePermission),
            1
        )
    }
}