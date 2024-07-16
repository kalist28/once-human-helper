package ru.kalistratov.oh.helper.core.app.factory

import com.arkivanov.decompose.ComponentContext
import ru.kalistratov.oh.helper.core.app.RootComponent
import ru.kalistratov.oh.helper.core.app.entity.AppScreen
import ru.kalistratov.oh.helper.core.app.entity.ScreenConfig
import ru.kalistratov.oh.helper.screen.main.MainComponent

interface AppScreenFactory {
    fun build(
        screenConfig: ScreenConfig,
        context: ComponentContext
    ): AppScreen
}

class AppScreenFactoryImpl(
    rootComponent: RootComponent
) : AppScreenFactory {

    private val routerFactory: AppRouterFactory = AppRouterFactoryImpl(rootComponent.navigation)

    override fun build(
        screenConfig: ScreenConfig,
        context: ComponentContext
    ): AppScreen = when (screenConfig) {
        ScreenConfig.Main -> MainComponent(
            context = context,
            router = routerFactory.get(screenConfig)
        ).let(AppScreen::Main)
    }
}
