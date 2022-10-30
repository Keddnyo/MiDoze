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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val model = MyViewModel()

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
                            if (model.isStateLoading()) {
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
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    text = model.getData(),
                                    style = TextStyle(fontSize = 72.sp),
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                onClick = {
                                    model.updateData()
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

class MyViewModel : ViewModel() {
    private var _someData : Int by mutableStateOf(0)
    private var isloading : Boolean by mutableStateOf(false)

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            isloading = true
            delay(3000)
            _someData++
            isloading = false
        }
    }

    fun getData(): String {
        return _someData.toString()
    }

    fun isStateLoading(): Boolean {
        return isloading
    }
}