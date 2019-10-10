package kep.mobile.common.domain.usecase

import kep.mobile.common.data.KepApi
import kep.mobile.common.domain.model.*

class GetTalkDetail(private val kepApi: KepApi): UseCase<Talk, String>() {
    override suspend fun run(params: String): Either<Exception, Talk> {
        return try {
            val talkEntity = kepApi.getTalks().find { it.id == params }
            if (talkEntity != null) {
                val speakerList = kepApi.getSpeakers()
                        .filter { it.id in talkEntity.speakers}.map { it.toModel() }
                print(speakerList)
                Success(talkEntity.toModel(speakers = speakerList.sortedBy(Speaker::name)))
            } else throw IllegalStateException("Talk $params doesn't exist")
        } catch (e: Exception) {
            Failure(e)
        }
    }
}