package kep.mobile.common

import kep.mobile.common.data.KepApi
import kep.mobile.common.domain.usecase.GetAboutInfo
import kep.mobile.common.domain.usecase.GetTalkDetail
import kep.mobile.common.domain.usecase.GetTalkList
import kep.mobile.common.presentation.AboutInfoPresenter
import kep.mobile.common.presentation.TalkDetailPresenter
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
    private val getTalkDetail: GetTalkDetail by lazy {
        GetTalkDetail(api)
    }
    private val getAboutInfo: GetAboutInfo by lazy {
        GetAboutInfo()
    }

    /**
     * PRESENTER
     */
    fun provideTalkListPresenter(): TalkListPresenter {
        return TalkListPresenter(getTalkList)
    }
    fun provideTalkDetailPresenter(): TalkDetailPresenter {
        return TalkDetailPresenter(getTalkDetail)
    }
    fun provideAboutPresenter(): AboutInfoPresenter {
        return AboutInfoPresenter(getAboutInfo)
    }
}
