package com.example.pexel.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pexel.di.ViewModelFactoryState
import com.example.pexel.di.daggerViewModel
import com.example.pexel.ui.details.component.BottomRow
import com.example.pexel.ui.details.component.DetailsPhotoItem
import com.example.pexel.ui.details.component.DetailsTopBar
import com.example.pexel.ui.details.component.ErrorDetails
import com.example.pexel.ui.details.data.DetailsScreenAction
import com.example.pexel.ui.details.data.DetailsScreenState

@Composable
fun DetailsScreen(
    viewModelFactoryState: ViewModelFactoryState,
    photoId: Int,
    isFromBookmarks: Boolean,
    onBackPress: () -> Unit,
    onNavigateBackClick: () -> Unit
) {
    val detailsViewModel =
        daggerViewModel<DetailsViewModel>(factory = viewModelFactoryState.viewModelFactory)
    val state by detailsViewModel.detailsScreenState.collectAsState()

    LaunchedEffect(photoId) {
        detailsViewModel.handleAction(
            DetailsScreenAction.Init(
                photoId = photoId,
                isFromNetwork = !isFromBookmarks
            )
        )
    }

    DetailsScreenContent(
        state = state,
        onBackPress = onBackPress,
        detailsActionHandler = { action ->
            if (action is DetailsScreenAction.BackPress) onBackPress()
            else detailsViewModel.handleAction(action)
        },
        onNavigateToHomeClick = onNavigateBackClick
    )
}

@Composable
private fun DetailsScreenContent(
    state: DetailsScreenState,
    onBackPress: () -> Unit,
    detailsActionHandler: (DetailsScreenAction) -> Unit,
    onNavigateToHomeClick: () -> Unit
) {
    when {
        state.isLoading -> LoadingState()
        state.isError -> ErrorDetails(onNavigateToHomeClick)
        else -> {
            val photo = state.photo ?: return ErrorDetails(onNavigateToHomeClick)
            SuccessState(photo, onBackPress, detailsActionHandler)
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessState(
    photo: com.example.pexel.domain.model.Photo,
    onBackPress: () -> Unit,
    detailsActionHandler: (DetailsScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsTopBar(
            photographer = photo.photographer,
            onBackPress = onBackPress
        )

        DetailsPhotoItem(photo = photo)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = photo.alt,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        BottomRow(
            onClick = { detailsActionHandler(DetailsScreenAction.Like) },
            liked = photo.liked,
            onDownloadClick = {
                detailsActionHandler(
                    DetailsScreenAction.Download(
                        photoId = photo.id,
                        imageUrl = photo.src.original
                    )
                )
            }
        )
    }
}
