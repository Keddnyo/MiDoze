package io.github.keddnyo.midoze.ui.routes.apps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.keddnyo.midoze.local.data_models.Application
import io.github.keddnyo.midoze.ui.routes.apps.AppsContainer.wearableApps

@Composable
fun AppsRoute() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp)
    ) {
        itemsIndexed(
            wearableApps
        ) { _: Int, app: Application ->
            Column(
                modifier = Modifier
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Image(
                        painter = painterResource(id = app.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .padding(5.dp),
                    )
                }
                Text(
                    text = app.name,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(5.dp),
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .padding(50.dp)
            )
        }
    }
}