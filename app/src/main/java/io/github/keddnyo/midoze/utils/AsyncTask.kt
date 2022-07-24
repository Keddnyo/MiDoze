package io.github.keddnyo.midoze.utils

import android.os.Handler
import android.os.Looper

abstract class AsyncTask {
    open val mainHandler = Handler(Looper.getMainLooper())
    open fun execute() {}
}