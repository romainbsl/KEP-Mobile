package kep.mobile.common.data

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import kotlinx.serialization.list

val BASE_URL: String = "everywhere.kotlin.paris"
val URL_PROTOCOL: URLProtocol = URLProtocol.HTTPS

class KepApi {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    private val speakerList = mutableListOf<SpeakerEntity>()
    private val talkList = mutableListOf<TalkEntity>()

    suspend fun getSpeakers(): List<SpeakerEntity> {
        if (speakerList.isEmpty()) {
            val json = client.get<String> {
                apiUrl("data/speakers.json")
            }

            speakerList.addAll(Json.parse(SpeakerEntity.serializer().list, json))
        }

        return speakerList
    }

    suspend fun getTalks(): List<TalkEntity> {
        if (talkList.isEmpty()) {
            val json = client.get<String> {
                apiUrl("data/talks.json")
            }

            talkList.addAll(Json.parse(TalkEntity.serializer().list, json))
        }

        return talkList
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            host = BASE_URL
            protocol = URL_PROTOCOL
            encodedPath = path
            header(HttpHeaders.Accept, "javascript/json")
        }
    }
}
