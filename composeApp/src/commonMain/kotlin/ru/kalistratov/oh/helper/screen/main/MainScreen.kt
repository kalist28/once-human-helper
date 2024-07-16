package ru.kalistratov.oh.helper.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil3.compose.LocalPlatformContext
import kotlinx.serialization.Serializable
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableZooming
import ovh.plrapps.mapcompose.ui.MapUI
import ovh.plrapps.mapcompose.ui.state.MapState
import ru.kalistratov.oh.helper.core.mvi.BaseIntent
import ru.kalistratov.oh.helper.createTileStreamProvider
import ru.kalistratov.oh.helper.getAsyncImageLoader

@Serializable
data class MarkerModel(
    val id: String,
    val latitude: Double,
    val longitude: Double,
)

sealed interface MainIntent : BaseIntent

val mapSize = pow2(16)

@Composable
fun MainScreen(component: MainComponent) {
    val tileStreamProvider = remember { createTileStreamProvider() }

    val context = LocalPlatformContext.current
    val loader = remember { getAsyncImageLoader(context) }

    val state = remember {
        MapState(16, mapSize, mapSize).apply {
            addLayer(tileStreamProvider)
            //enableRotation()
            enableZooming()
        }
    }

    MapUI(Modifier, state = state)
}

fun pow2(pow: Int): Int {
    var sum = 1
    for (i in 1..pow) sum *= 2
    return sum
}
