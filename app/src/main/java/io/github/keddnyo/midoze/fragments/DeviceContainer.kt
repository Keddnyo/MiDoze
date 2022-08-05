package io.github.keddnyo.midoze.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.local.dataModels.FirmwareDataStack

class DeviceContainer {
    fun show(context: AppCompatActivity, stackArray: ArrayList<FirmwareDataStack>, position: Int) =
        with(context) {
            DeviceFragment().let { deviceFragment ->
                deviceFragment.arguments = Bundle().apply {
                    putString(
                        "deviceArray",
                        Gson().toJson(stackArray[position].deviceStack)
                    )
                }

                supportFragmentManager.let {
                    if (!it.isDestroyed) {
                        it.beginTransaction()
                            .replace(R.id.deviceFrame, deviceFragment)
                            .commit()
                    }
                }

                title = stackArray[position].name
            }
        }
}