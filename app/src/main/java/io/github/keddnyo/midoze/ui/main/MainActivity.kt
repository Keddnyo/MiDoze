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
                            if (model.isLoading) {
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
                                    text = model.someData.value.toString(),
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
    var someData: MutableLiveData<Int> = MutableLiveData(0)
    var isLoading : Boolean by mutableStateOf(false)

    fun updateData() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            delay(3000)
            someData.postValue(someData.value?.plus(1))
            isLoading = false
        }
    }
}