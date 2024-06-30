package ru.kalistratov.duckside.raidhelper.core.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import org.koin.core.component.KoinComponent
import ru.kalistratov.duckside.raidhelper.core.app.entity.AppScreen
import ru.kalistratov.duckside.raidhelper.core.app.entity.ScreenConfig
import ru.kalistratov.duckside.raidhelper.core.app.factory.AppScreenFactory
import ru.kalistratov.duckside.raidhelper.core.app.factory.AppScreenFactoryImpl

typealias AppNavigation = StackNavigation<ScreenConfig>

interface RootComponent : ComponentContext, KoinComponent {
    val stack: Value<ChildStack<*, AppScreen>>

    val navigation: StackNavigation<ScreenConfig>

    val backDispatcher: BackDispatcher

    fun onBack()
}

class RootComponentImpl(
    context: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by context {

    override val navigation = StackNavigation<ScreenConfig>()

    private val childFactory: AppScreenFactory = AppScreenFactoryImpl(this)

    override val stack: Value<ChildStack<*, AppScreen>> = childStack(
        source = navigation,
        serializer = ScreenConfig.serializer(),
        initialConfiguration = ScreenConfig.Main,
        handleBackButton = true,
        childFactory = childFactory::build
    )

    override val backDispatcher: BackDispatcher = object : BackDispatcher by BackDispatcher() {

        override val isEnabled: Boolean
            get() = stack.backStack.isNotEmpty()

        override fun back(): Boolean = run {
            onBack()
            true
        }
    }

    override fun onBack() = navigation.pop()
}