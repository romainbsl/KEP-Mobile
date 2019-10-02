package kep.mobile.common.domain.usecase

import kep.mobile.common.data.KepApi
import kep.mobile.common.domain.model.*

class GetTalkList(private val kepApi: KepApi): UseCase<List<Talk>, UseCase.None>() {
    override suspend fun run(params: None): Either<Exception, List<Talk>> {
        return try {
            val talkList = kepApi.getTalks().map { it.toModel() }
            Success(talkList)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}