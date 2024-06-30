package ru.kalistratov.duckside.raidhelper.screen.main

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import ru.kalistratov.duckside.raidhelper.core.mvi.BaseAction
import ru.kalistratov.duckside.raidhelper.core.mvi.BaseViewState
import ru.kalistratov.duckside.raidhelper.core.mvi.ScreenComponent

@Stable
data class MainScreen(
    val text: String = ""
) : BaseViewState

sealed interface MainAction : BaseAction {

}

@Immutable
class MainComponent(
    context: ComponentContext,
    private val router: MainRouter
) : ScreenComponent<MainIntent, MainAction, MainScreen>(context) {

    override fun start() {

    }

    override fun initialState() = MainScreen()

    override fun reduce(
        state: MainScreen,
        action: MainAction
    ): MainScreen = when (action) {
        else -> state
    }
}