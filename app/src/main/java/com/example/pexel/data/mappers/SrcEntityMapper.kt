package com.example.pexel.data.mappers

import com.example.pexel.data.database.SrcEntity
import com.example.pexel.domain.model.Src
import javax.inject.Inject

class SrcEntityMapper @Inject constructor() {

    operator fun invoke(srcEntity: SrcEntity): Src = with(srcEntity) {
        return Src(
            landscape = landscape,
            large = large,
            large2x = large2x,
            medium = medium,
            original = original,
            portrait = portrait,
            small = small,
            tiny = tiny
        )
    }
}