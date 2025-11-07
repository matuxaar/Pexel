package com.example.pexel.data.paging

import androidx.paging.RemoteMediator
import com.example.pexel.data.database.PhotoEntity


class PhotoRemoteMediator(): RemoteMediator<Int, PhotoEntity>() {
}