package kep.mobile.common

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import kep.mobile.common.data.Speaker
import kep.mobile.common.data.Talk
import kotlinx.serialization.list

expect val endPoint: String

class DataApi {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                register(Speaker.serializer().list)
                register(Talk.serializer().list)
            }
        }
    }

    suspend fun getSpeakers(): List<Speaker> = client.get {
        apiUrl("data/speakers.json")
    }

    suspend fun getTalks(): List<Talk> = client.get {
        apiUrl("data/talks.json")
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        header(HttpHeaders.Accept, "javascript/json")
        url {
            takeFrom(endPoint)
            encodedPath = path
        }
    }
}
