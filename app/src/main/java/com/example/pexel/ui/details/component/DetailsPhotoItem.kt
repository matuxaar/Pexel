package com.example.pexel.ui.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pexel.R
import com.example.pexel.domain.model.Photo

@Composable
fun DetailsPhotoItem(photo: Photo) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Transparent)
            .padding(24.dp)

    ) {
        AsyncImage(
            model = photo.src.original,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_placeholder),
            modifier = Modifier.fillMaxSize()
        )
    }
}