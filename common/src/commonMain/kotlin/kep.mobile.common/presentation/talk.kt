package kep.mobile.common.presentation

import kep.mobile.common.UIDispatcher
import kep.mobile.common.data.KepApi
import kep.mobile.common.data.TalkEntity
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TalkPresenter(
    private val api: KepApi,
    coroutineContext: CoroutineContext = UIDispatcher // TODO move onto ApplicationDispatcher
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
    fun onSuccessGetTalkList(talkList: List<TalkEntity>)
}

