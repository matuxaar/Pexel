package com.example.pexel.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pexel.R

@Composable
fun ExploreButton(onErrorClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { }
    ) {
        Text(
            text = stringResource(R.string.explore),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { onErrorClick() }
        )
    }
}

@Composable
fun TryAgainButton(onTryAgainClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { }
    ) {
        Text(
            text = stringResource(R.string.try_again),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { onTryAgainClick() }
        )
    }
}