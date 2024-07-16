package ru.kalistratov.oh.helper

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.request.prepareGet
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import okio.FileSystem

actual fun getClient(): HttpClient = HttpClient(Darwin) {
    engine {
        this.
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}

actual val fileSystem: FileSystem
    get() = FileSystem.SYSTEM

val client = getClient()

actual suspend fun getUrlSource(url: String): RawSource = Buffer().apply {
    client.prepareGet(url).execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()

            /*val fileBuffer = fileSystem.sink(filePath)
                .buffer()*/

            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(8192)
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()
                    write(bytes, 0, bytes.size)
                    //  fileBuffer.write(bytes, 0, bytes.size)
                }
            }
            //  fileBuffer.close()
        }
    }