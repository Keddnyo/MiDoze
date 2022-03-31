package io.github.keddnyo.midoze.utils

import android.content.Context
import android.widget.Toast

class UiUtils {
    fun makeToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}