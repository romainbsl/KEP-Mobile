package kep.mobile.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

internal actual val ApplicationDispatcher: CoroutineContext = Dispatchers.Main
//internal actual val ApplicationDispatcher: CoroutineContext = Dispatchers.IO TODO