package io.github.keddnyo.midoze.utils

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import io.github.keddnyo.midoze.R

class UiUtils {
    fun getRecyclerSpanCount(context: Context): Int = with(context) {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            1
        } else {
            2
        }
    }
    fun makeToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}