import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ru.kalistratov.duckside.raidhelper.App
import platform.UIKit.UIViewController
import ru.kalistratov.duckside.raidhelper.core.app.RootComponent
import ru.kalistratov.duckside.raidhelper.core.app.RootComponentImpl

fun RootComponent(lifecycleRegistry: LifecycleRegistry): RootComponent =
    RootComponentImpl(DefaultComponentContext(lifecycleRegistry))

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
