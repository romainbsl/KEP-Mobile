package kep.mobile.common.presentation

import kep.mobile.common.ApplicationDispatcher
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.domain.usecase.GetTalkDetail
import kep.mobile.common.domain.usecase.GetTalkList
import kep.mobile.common.domain.usecase.UseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TalkListPresenter(
        private val getTalkList: GetTalkList,
        coroutineContext: CoroutineContext = ApplicationDispatcher
) : BasePresenter<TalkListView>(coroutineContext) {

    override fun onViewAttached(view: TalkListView) {
        scope.launch {
            getTalkList(
                    UseCase.None,
                    onSuccess = { view.onSuccessGetTalkList(it) },
                    onFailure = { view.onFailureGetTalkList(it) }
            )
        }
    }
}

interface TalkListView {
    fun onSuccessGetTalkList(talkList: List<Talk>)
    fun onFailureGetTalkList(e: Exception)
    fun checkInternetConnection(): Boolean
}


class TalkDetailPresenter(
        private val getTalkDetail: GetTalkDetail,
        coroutineContext: CoroutineContext = ApplicationDispatcher
) : BasePresenter<TalkDetailView>(coroutineContext) {

    override fun onViewAttached(view: TalkDetailView) { }

    fun getCurrentTalk(talkId: String) {
        scope.launch {
            getTalkDetail(
                    talkId,
                    onSuccess = { view?.onSuccessGetTalkDetail(it) },
                    onFailure = { view?.onFailureGetTalkDetail(it) }
            )
        }
    }
}

interface TalkDetailView {
    fun onSuccessGetTalkDetail(talk: Talk)
    fun onFailureGetTalkDetail(e: Exception)
}