package kep.mobile.common

import kep.mobile.common.model.data.Talk
import kep.mobile.common.presentation.BasePresenter
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TalkPresenter(
    private var api: DataApi,
    coroutineContext: CoroutineContext = ApplicationDispatcher
) : BasePresenter<TalkView>(coroutineContext) {

    override fun onViewAttached(view: TalkView) {

    }

    fun getTalkList() {
        scope.launch {

            val talkList = api.getTalks()
            if (talkList.isNotEmpty()) {
                view?.onSuccessGetTalkList(talkList)
            }
        }
    }
}

interface TalkView {
    fun onSuccessGetTalkList(talkList: List<Talk>)
}

