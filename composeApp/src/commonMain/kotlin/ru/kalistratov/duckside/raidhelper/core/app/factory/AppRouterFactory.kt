package ru.kalistratov.duckside.raidhelper.core.app.factory

import com.arkivanov.decompose.router.stack.StackNavigation
import ru.kalistratov.duckside.raidhelper.core.app.entity.ScreenConfig
import ru.kalistratov.duckside.raidhelper.screen.main.MainComponent
import ru.kalistratov.duckside.raidhelper.screen.main.MainRouterImpl

interface AppRouterFactory {
    fun create(screenConfig: ScreenConfig): Any
}

inline fun <reified R> AppRouterFactory.get(
    screenConfig: ScreenConfig
): R = create(screenConfig) as R

class AppRouterFactoryImpl(
    private val navigator: StackNavigation<ScreenConfig>,
) : AppRouterFactory {
    override fun create(
        screenConfig: ScreenConfig
    ) = when (screenConfig) {
        ScreenConfig.Main -> MainRouterImpl(navigator)
    }
}