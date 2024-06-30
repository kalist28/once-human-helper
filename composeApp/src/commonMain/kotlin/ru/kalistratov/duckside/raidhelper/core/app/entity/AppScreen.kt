package ru.kalistratov.duckside.raidhelper.core.app.entity

import ru.kalistratov.duckside.raidhelper.screen.main.MainComponent

sealed interface AppScreen {
    data class Main(val component: MainComponent) : AppScreen
}