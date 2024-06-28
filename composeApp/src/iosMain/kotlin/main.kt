import androidx.compose.ui.window.ComposeUIViewController
import ru.kalistratov.duckside.raidhelper.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
