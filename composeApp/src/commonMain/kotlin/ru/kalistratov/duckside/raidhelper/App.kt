package ru.kalistratov.duckside.raidhelper

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.IndieFlower_Regular
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.Res
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.cyclone
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.ggg
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.ic_cyclone
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.ic_dark_mode
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.ic_light_mode
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.ic_rotate_right
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.open_github
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.run
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.stop
import ru.kalistratov.duckside.raidhelper.composeapp.generated.resources.theme
import ru.kalistratov.duckside.raidhelper.theme.AppTheme
import ru.kalistratov.duckside.raidhelper.theme.LocalThemeIsDark

@Composable
internal fun App() = AppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.cyclone),
            fontFamily = FontFamily(Font(Res.font.IndieFlower_Regular)),
            style = MaterialTheme.typography.displayLarge
        )

        var isAnimate by remember { mutableStateOf(false) }
        val transition = rememberInfiniteTransition()
        val rotate by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing)
            )
        )

        Image(
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
                .run { if (isAnimate) rotate(rotate) else this },
            imageVector = vectorResource(Res.drawable.ic_cyclone),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentDescription = null
        )

        ElevatedButton(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .widthIn(min = 200.dp),
            onClick = { isAnimate = !isAnimate },
            content = {
                Icon(vectorResource(Res.drawable.ic_rotate_right), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(if (isAnimate) Res.string.stop else Res.string.run)
                )
            }
        )

        var isDark by LocalThemeIsDark.current
        val icon = remember(isDark) {
            if (isDark) Res.drawable.ic_light_mode
            else Res.drawable.ic_dark_mode
        }

        ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { isDark = !isDark },
            content = {
                Icon(vectorResource(icon), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(Res.string.theme))
            }
        )

        TextButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { openUrl("https://github.com/terrakok") },
        ) {
            Text(stringResource(Res.string.open_github))
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) = ImageLoader
    .Builder(context)
    .crossfade(true)
    .logger(DebugLogger())
    .build()


internal expect fun openUrl(url: String?)