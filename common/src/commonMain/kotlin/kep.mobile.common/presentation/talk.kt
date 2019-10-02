package kep.mobile.common.presentation

import kep.mobile.common.ApplicationDispatcher
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.domain.usecase.GetTalkList
import kep.mobile.common.domain.usecase.UseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TalkListPresenter(
    private val getTalkList: GetTalkList,
    coroutineContext: CoroutineContext = ApplicationDispatcher
) : BasePresenter<TalkView>(coroutineContext) {

    override fun onViewAttached(view: TalkView) {
        scope.launch {
            getTalkList(
                UseCase.None,
                onSuccess = { view.onSuccessGetTalkList(it) },
                onFailure = { view.onFailureGetTalkList(it) }
            )
        }
    }
}

interface TalkView {
    fun onSuccessGetTalkList(talkList: List<Talk>)
    fun onFailureGetTalkList(e: Exception)
}

