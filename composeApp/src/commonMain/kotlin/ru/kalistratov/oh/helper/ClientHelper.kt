package ru.kalistratov.oh.helper

import io.ktor.client.HttpClient
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.serialization.ExperimentalSerializationApi
import okio.FileSystem
import okio.Path.Companion.toPath
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ru.kalistratov.oh.helper.screen.main.pow2

private val rootUrl = "https://tiles.mapgenie.io/games/once-human/nalcott/default-v3/"

expect fun getClient(): HttpClient

expect val fileSystem: FileSystem

expect suspend fun getUrlSource(url: String): RawSource

fun createTileStreamProvider() =
    TileStreamProvider { row, col, zoomLvl -> //use the compression format of your need
        try {
            val size = pow2(zoomLvl)
            val size2 = pow2(zoomLvl + 1)
            val count = size2 / 256

            val urlZoom = zoomLvl + 1
            val urlRow = size - (count - row)
            val urlCol = size - (count - col)

            val fileKey = "$urlZoom/$urlRow/$urlCol.jpg"
            val url = "$rootUrl/$fileKey"

            val fileSystemPath = FileSystem.SYSTEM_TEMPORARY_DIRECTORY

            val path = fileSystemPath / "$zoomLvl/$urlRow/".toPath()
            val filePath = ("$fileSystemPath/$fileKey").toPath()

            val pathExists = fileSystem.exists(path)
            if (!pathExists) fileSystem.createDirectories(path)

            val fileExist = fileSystem.exists(filePath)

            if (fileExist) Buffer().also {
                fileSystem.read(filePath) {
                    it.write(readByteArray())
                }
            } else getUrlSource(url)
        } catch (e: Exception) {
            println(e)
            null
        }
    }

@OptIn(ExperimentalSerializationApi::class)
val jsonParser = kotlinx.serialization.json.Json {
    explicitNulls = false

    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
    encodeDefaults = true
    useAlternativeNames = true
    coerceInputValues = true
    allowStructuredMapKeys = true
    useArrayPolymorphism = true
    /*serializersModule = SerializersModule {
        contextual(WeekDaySerializer)
        contextual(ClockFormatTimeSerializer)
    }*/
}