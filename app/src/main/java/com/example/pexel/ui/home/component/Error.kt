package com.example.pexel.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pexel.R
import com.example.pexel.ui.component.ExploreButton
import com.example.pexel.ui.component.TryAgainButton

@Composable
fun ErrorHome(onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_wifi_error),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(0.5f),
        )
        TryAgainButton(
            onTryAgainClick = onTryAgainClick
        )
    }
}

@Composable
fun EmptyHomeScreen(
    onTryAgainClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.you_haven_t_saved_anything_yet),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        ExploreButton(onErrorClick = onTryAgainClick)
    }
}