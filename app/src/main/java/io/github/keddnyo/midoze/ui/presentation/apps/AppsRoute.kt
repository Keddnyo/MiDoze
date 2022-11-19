package io.github.keddnyo.midoze.ui.presentation.apps

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.keddnyo.midoze.internal.CardContentOffset
import io.github.keddnyo.midoze.local.repositories.applications.wearableApplications

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppsRoute() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp)
    ) {
        wearableApplications.forEach { app ->
            item {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        elevation = CardDefaults.outlinedCardElevation(2.dp),
                    ) {
                        Image(
                            painter = painterResource(id = app.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(128.dp)
                                .padding(5.dp)
                        )
                    }
                    Text(
                        text = app.name,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(CardContentOffset),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}