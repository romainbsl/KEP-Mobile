package kep.mobile.common

import kep.mobile.common.data.KepApi
import kep.mobile.common.presentation.TalkPresenter
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
     * PRESENTER
     */
    fun provideTalkPresenter(): TalkPresenter {
        return TalkPresenter(api)
    }
}
