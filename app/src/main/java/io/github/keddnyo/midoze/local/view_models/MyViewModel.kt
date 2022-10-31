package io.github.keddnyo.midoze.local.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    // Represents the state of firmware loading coroutine job
    private var _isFirmwareListLoading by mutableStateOf(false)
    fun isFirmwareListLoading() = _isFirmwareListLoading

    // FirmwareList stores firmwares list
    private var _firmwareList = MutableLiveData(0)
    fun firmwareList() = _firmwareList.value.toString()

    // Firmware loading coroutine job
    fun updateData() {

        viewModelScope.launch(Dispatchers.IO) {

            // Sets the state of firmware loading coroutine job to true
            _isFirmwareListLoading = true

            // Sets delay for 3000 millis
            delay(3000)

            // Increases FirmwareList value
            _firmwareList.postValue(_firmwareList.value?.plus(1))

            // Sets the state of firmware loading coroutine job to false
            _isFirmwareListLoading = false

        }

    }

}