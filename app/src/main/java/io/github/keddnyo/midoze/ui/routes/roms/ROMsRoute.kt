package io.github.keddnyo.midoze.ui.routes.roms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.keddnyo.midoze.local.data_models.firmware.FirmwareStack
import io.github.keddnyo.midoze.remote.requests.firmware.getFirmwareStack
import kotlinx.coroutines.runBlocking

@Composable
fun ROMsRoute() {
    val firmwareStackArray: ArrayList<FirmwareStack> = runBlocking {
        getFirmwareStack()
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp)
    ) {
        itemsIndexed(
            firmwareStackArray
        ) { _: Int, firmwareStack: FirmwareStack ->
            Card(
                modifier = Modifier
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column {
                    firmwareStack.stack[0].firmwareVersion?.let {
                        Text(
                            text = it,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(5.dp),
                        )
                    }
                }
            }
        }
    }
}