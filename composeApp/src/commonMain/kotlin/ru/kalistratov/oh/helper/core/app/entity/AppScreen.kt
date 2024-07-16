package ru.kalistratov.oh.helper.core.app.entity

import ru.kalistratov.oh.helper.screen.main.MainComponent

sealed interface AppScreen {
    data class Main(val component: MainComponent) : AppScreen
}