package io.github.keddnyo.midoze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.keddnyo.midoze.R
import io.github.keddnyo.midoze.adapters.firmware.DeviceAdapter
import io.github.keddnyo.midoze.local.dataModels.Firmware
import io.github.keddnyo.midoze.local.menu.Dimens.CARD_GRID_WIDTH
import io.github.keddnyo.midoze.utils.Display

class DeviceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(requireContext()) {
        super.onViewCreated(view, savedInstanceState)

        val deviceListRecyclerView: RecyclerView = view.findViewById(R.id.deviceRecyclerView)
        deviceListRecyclerView.layoutManager =
            GridLayoutManager(
                this, Display()
                    .getGridLayoutIndex(this, CARD_GRID_WIDTH)
            )

        val adapter = DeviceAdapter()
        deviceListRecyclerView.adapter = adapter

        val firmwaresDataArray: ArrayList<Firmware.FirmwareData> = GsonBuilder().create().fromJson(
            arguments?.getString("deviceArray"),
            object : TypeToken<ArrayList<Firmware.FirmwareData>>() {}.type
        )

        adapter.addDevice(firmwaresDataArray)
    }
}