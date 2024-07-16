package ru.kalistratov.oh.helper.core.app.entity

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenConfig {

    @Serializable
    data object Main : ScreenConfig
}