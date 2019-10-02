package kep.mobile.common.data

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import kotlinx.serialization.list

expect val BASE_URL: String
expect val BASE_PORT: Int
expect val URL_PROTOCOL: URLProtocol

class KepApi {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                register(SpeakerEntity.serializer().list)
                register(TalkEntity.serializer().list)
            }
        }
    }

    private val speakerList = mutableListOf<SpeakerEntity>()
    private val talkList = mutableListOf<TalkEntity>()

    suspend fun getSpeakers(): List<SpeakerEntity> {
        if (speakerList.isEmpty())
            speakerList.addAll(client.get {
                apiUrl("data/speakers.json")
            } as List<SpeakerEntity>)

        return speakerList
    }

    suspend fun getTalks(): List<TalkEntity> {
        if (talkList.isEmpty())
            talkList.addAll(client.get {
                apiUrl("data/talks.json")
            } as List<TalkEntity>)

        return talkList
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            host = BASE_URL
            port = BASE_PORT
            protocol = URL_PROTOCOL
            encodedPath = path
            header(HttpHeaders.Accept, "javascript/json")
        }
    }
}
