package com.example.pexel.data.mappers

import com.example.pexel.data.models.CollectionResponse
import com.example.pexel.domain.model.Collection
import javax.inject.Inject

class OneCollectionMapper @Inject constructor() {

    operator fun invoke(response: CollectionResponse): Collection = with(response) {
        return Collection(
            title = title
        )
    }

}