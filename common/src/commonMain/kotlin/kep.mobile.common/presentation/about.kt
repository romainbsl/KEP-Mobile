package kep.mobile.common.presentation

import kep.mobile.common.ApplicationDispatcher
import kep.mobile.common.domain.model.About
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.domain.usecase.GetAboutInfo
import kep.mobile.common.domain.usecase.GetTalkDetail
import kep.mobile.common.domain.usecase.GetTalkList
import kep.mobile.common.domain.usecase.UseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AboutInfoPresenter(
        private val getAboutInfo: GetAboutInfo,
        coroutineContext: CoroutineContext = ApplicationDispatcher
) : BasePresenter<AboutInfoView>(coroutineContext) {
    override fun onViewAttached(view: AboutInfoView) {
        scope.launch {
            getAboutInfo(
                    UseCase.None,
                    onSuccess = { view.onSuccessGetAboutInfo(it) },
                    onFailure = { view.onFailureGetAboutInfo(it) }
            )
        }
    }
}

interface AboutInfoView {
    fun onSuccessGetAboutInfo(about: About)
    fun onFailureGetAboutInfo(e: Exception)
}