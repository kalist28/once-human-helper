package ru.kalistratov.duckside.raidhelper.core.app.entity

import ru.kalistratov.oh.helper.screen.main.MainComponent

sealed interface AppScreen {
    data class Main(val component: MainComponent) : AppScreen
}