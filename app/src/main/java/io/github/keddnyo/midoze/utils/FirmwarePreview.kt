package io.github.keddnyo.midoze.utils

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView

abstract class FirmwarePreview {
    open lateinit var preview: ImageView

    open lateinit var description: TextView

    open lateinit var payload: TextView

    open lateinit var share: ImageView

    open lateinit var download: ImageView

    open lateinit var downloadContent: String

    open fun main() {}

    open fun setOnShareClickListener(unit: () -> Unit) {
        share.setOnClickListener {
            unit()
        }
    }

    open fun setOnDownloadClickListener(unit: () -> Unit) {
        download.setOnClickListener {
            unit()
        }
    }
}