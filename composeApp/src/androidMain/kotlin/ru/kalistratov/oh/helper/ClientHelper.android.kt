package ru.kalistratov.oh.helper

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.asSource
import okio.FileSystem
import java.net.URL

actual fun getClient(): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(jsonParser)
    }
}

actual val fileSystem: FileSystem
    get() = FileSystem.SYSTEM

actual suspend fun getUrlSource(url: String) = URL(url)
    .openStream()
    .asSource()