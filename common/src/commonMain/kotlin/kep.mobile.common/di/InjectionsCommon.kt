package kep.mobile.common

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object InjectorCommon {
    /**
     * API
     */
    private val api: DataApi by lazy {
        DataApi()
    }

    /**
     * PRESENTER
     */
    fun provideTalkPresenter(): TalkPresenter {
        return TalkPresenter(api)
    }
}
