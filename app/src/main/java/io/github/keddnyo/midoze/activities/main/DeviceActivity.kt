package io.github.keddnyo.midoze.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.activities.main.adapters.DeviceAdapter
import io.github.keddnyo.midoze.local.dataModels.FirmwareData
import io.github.keddnyo.midoze.utils.Display

class DeviceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra("NAME")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_device)

        val deviceListRecyclerView: RecyclerView = findViewById(R.id.deviceRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(
                this, Display()
                    .getGridLayoutIndex(this, 200)
            )

        val adapter = DeviceAdapter()
        deviceListRecyclerView.adapter = adapter

        val firmwaresDataArray: ArrayList<FirmwareData> = GsonBuilder().create().fromJson(
            intent.getStringExtra("DEVICE_ARRAY"),
            object : TypeToken<ArrayList<FirmwareData>>() {}.type
        )

        adapter.addDevice(firmwaresDataArray)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}