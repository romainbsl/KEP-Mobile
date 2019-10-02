package kep.mobile.common

import kotlin.coroutines.CoroutineContext

internal expect val ApplicationDispatcher: CoroutineContext
internal expect val UIDispatcher: CoroutineContext