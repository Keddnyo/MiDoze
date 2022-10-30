package io.github.keddnyo.midoze.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var model: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            model = ViewModelProvider(this)[MyViewModel::class.java]

            model.run {
                MiDozeTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = firmwareList,
                                    style = TextStyle(fontSize = 72.sp),
                                    textAlign = TextAlign.Center,
                                )

                                if (isFirmwareListLoading) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(10.dp)
                                        )
                                    }
                                } else {
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        onClick = {
                                            updateData()
                                        }
                                    ) {
                                        Text(
                                            text = "Increase",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class MyViewModel : ViewModel() {

    // Represents the state of firmware loading coroutine job
    var isFirmwareListLoading by mutableStateOf(false)

    // FirmwareList stores firmwares list
    private var _firmwareList = MutableLiveData(0)

    // Returns firmware list formatted as String
    val firmwareList: String = _firmwareList.toString()

    // Firmware loading coroutine job
    fun updateData() {

        viewModelScope.launch(Dispatchers.IO) {

            // Sets the state of firmware loading coroutine job to true
            isFirmwareListLoading = true

            // Sets delay for 3000 millis
            delay(3000)

            // Increases FirmwareList value
            _firmwareList.value = _firmwareList.value?.plus(1)

            // Sets the state of firmware loading coroutine job to false
            isFirmwareListLoading = false

        }

    }

}