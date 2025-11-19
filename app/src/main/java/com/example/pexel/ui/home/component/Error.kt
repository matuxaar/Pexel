package com.example.pexel.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pexel.R
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
                .fillMaxSize(),
        )
        TryAgainButton(
            onTryAgainClick = onTryAgainClick
        )
    }
}