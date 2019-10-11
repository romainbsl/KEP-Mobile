package kep.mobile.common.domain.usecase

import kep.mobile.common.domain.model.*

class GetAboutInfo(): UseCase<About, UseCase.None>() {
    override suspend fun run(params: None): Either<Exception, About> {
        return try {
            Success(About(about, websiteUri, twitterUri, Location(placeName, location.first, location.second)))
        } catch (e: Exception) {
            Failure(e)
        }
    }
}