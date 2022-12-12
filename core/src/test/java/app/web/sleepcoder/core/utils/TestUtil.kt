package app.web.sleepcoder.core.utils

import org.mockito.Mockito

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T> uninitialized(): T = null as T