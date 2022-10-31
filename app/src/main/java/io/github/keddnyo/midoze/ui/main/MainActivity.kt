package io.github.keddnyo.midoze.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import io.github.keddnyo.midoze.local.view_models.MyViewModel
import io.github.keddnyo.midoze.ui.theme.MiDozeTheme

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
                                    text = firmwareList(),
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