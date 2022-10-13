package io.github.keddnyo.midoze.ui.main
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.keddnyo.midoze.R

@Composable
fun Title(title: String?) {
    Text(
        text = title ?: stringResource(id = R.string.app_name)
    )
}