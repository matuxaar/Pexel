package com.example.pexel.data.mappers

import com.example.pexel.data.models.SrcResponse
import com.example.pexel.domain.model.Src
import javax.inject.Inject

class SrcResponseMapper @Inject constructor() {

    operator fun invoke(srcResponse: SrcResponse): Src = with(srcResponse) {
        return Src(
            landscape = landscape.orEmpty(),
            large = large.orEmpty(),
            large2x = large2x.orEmpty(),
            medium = medium.orEmpty(),
            original = original.orEmpty(),
            portrait = portrait.orEmpty(),
            small = small.orEmpty(),
            tiny = tiny.orEmpty()
        )
    }

}