package io.github.keddnyo.midoze.utils

import android.widget.ImageView
import android.widget.TextView

abstract class FirmwarePreview {
    open lateinit var preview: ImageView
    open lateinit var description: TextView
    open lateinit var downloadContent: String

    open fun main() {}
}