package kep.mobile.common.domain.usecase

import kep.mobile.common.data.KepApi
import kep.mobile.common.domain.model.*

class GetTalkDetail(private val kepApi: KepApi): UseCase<Talk, Talk>() {
    override suspend fun run(params: Talk): Either<Exception, Talk> {
        return try {
            val talkEntity = kepApi.getTalks().find { it.id == params.id }
            val speakerList = kepApi.getSpeakers()
                .filter { it.id in talkEntity?.speakers ?: emptyList()}.map { it.toModel() }
            Success(params.copy(speakers = speakerList))
        } catch (e: Exception) {
            Failure(e)
        }
    }
}