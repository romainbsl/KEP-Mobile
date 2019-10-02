package kep.mobile.common

import kep.mobile.common.data.KepApi
import kep.mobile.common.domain.usecase.GetTalkList
import kep.mobile.common.presentation.TalkListPresenter
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object InjectorCommon {
    /**
     * API
     */
    private val api: KepApi by lazy {
        KepApi()
    }

    /**
     * USECASE
     */
    private val getTalkList: GetTalkList by lazy {
        GetTalkList(api)
    }

    /**
     * PRESENTER
     */
    fun provideTalkPresenter(): TalkListPresenter {
        return TalkListPresenter(getTalkList)
    }
}
