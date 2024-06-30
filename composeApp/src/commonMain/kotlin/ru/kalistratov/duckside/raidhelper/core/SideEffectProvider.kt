package ru.kalistratov.duckside.raidhelper.core

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Immutable
interface SideEffectProvider<E : SideEffect> {
    val effects: Flow<E>
}

interface SideEffectSupport<E : SideEffect> : SideEffectProvider<E> {
    fun postEffect(effect: E)
}

class SideEffectSupportImpl<E : SideEffect> : SideEffectSupport<E> {
    private val _effects = MutableSharedFlow<E>()

    override val effects: Flow<E>
        get() = _effects.asSharedFlow()

    override fun postEffect(effect: E) {
        _effects.tryEmit(effect)
    }
}