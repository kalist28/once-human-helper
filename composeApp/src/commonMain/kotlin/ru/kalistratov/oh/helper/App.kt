package ru.kalistratov.oh.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.github.alexzhirkevich.cupertino.decompose.NativeChildren
import ru.kalistratov.oh.helper.core.app.RootComponent
import ru.kalistratov.oh.helper.core.app.entity.AppScreen
import ru.kalistratov.oh.helper.screen.main.MainScreen
import ru.kalistratov.oh.helper.theme.AppTheme


@Composable
fun App(rootComponent: RootComponent) = AppTheme {
    NativeChildren(
        stack = rootComponent.stack,
        onBack = { rootComponent.backDispatcher.back() },
        modifier = Modifier
    ) { child ->
        when (val instance = child.instance) {
            is AppScreen.Main -> MainScreen(instance.component)
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) = ImageLoader
    .Builder(context)
    .crossfade(true)
    .logger(DebugLogger())
    .build()


internal expect fun openUrl(url: String?)