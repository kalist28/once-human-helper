@file:Suppress("unused", "FunctionName")

package ru.kalistratov.oh.helper

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import platform.UIKit.UIViewController
import ru.kalistratov.oh.helper.core.app.RootComponent
import ru.kalistratov.oh.helper.core.app.RootComponentImpl

fun RootComponent(lifecycleRegistry: LifecycleRegistry): RootComponent =
    RootComponentImpl(DefaultComponentContext(lifecycleRegistry))

fun MainViewController(component: RootComponent): UIViewController {
    return ComposeUIViewController(
        configure = {
            onFocusBehavior = OnFocusBehavior.DoNothing
        }
    ) {
        App(component)
    }
}