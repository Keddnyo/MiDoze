package io.github.keddnyo.midoze.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.keddnyo.midoze.DeviceFragment
import io.github.keddnyo.midoze.R

class DeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra("NAME")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val deviceFragment = DeviceFragment()
        val args = Bundle()
        args.putString("DEVICE_ARRAY", intent.getStringExtra("DEVICE_ARRAY"))
        deviceFragment.arguments = args

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.deviceFragmentPhone, deviceFragment)
            .commit()

        if (resources.getBoolean(R.bool.isTablet)) {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}