package ru.kalistratov.duckside.raidhelper.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import org.koin.core.component.KoinComponent

abstract class ScreenComponent<I, A, S>(context: ComponentContext) :
    ComponentContext by context,
    KoinComponent where I : BaseIntent, A : BaseAction, S : BaseViewState {

    protected open val screenName = "ScreenComponent"
    protected val screenScope by lazy {
        val dispatcher = Dispatchers.Main.immediate
        val name = CoroutineName(this::class.simpleName ?: screenName)
        CoroutineScope(dispatcher + name)
    }

    protected val stateFlow by lazy { MutableStateFlow(initialState()) }
    protected val intentFlow: MutableSharedFlow<I> = MutableSharedFlow(replay = 1)

    init {
        doOnCreate { start() }
        doOnDestroy { screenScope.cancel() }
    }

    protected val state get() = stateFlow.value

    abstract fun start()

    abstract fun initialState(): S

    abstract fun reduce(state: S, action: A): S

    @Composable
    fun collectState(): State<S> = stateFlow
        .collectAsState()

    fun post(intent: I) =
        intentFlow.tryEmit(intent)

    protected fun Flow<A>.collectState(
        initialState: S = stateFlow.replayCache.firstOrNull() ?: initialState()
    ) = scan(initialState, ::reduce)
        .distinctUntilChanged()
        .onEach(stateFlow::emit)
        .launchIn(screenScope)


    protected inline fun <reified R : I> intentOf() =
        intentFlow.filterIsInstance<R>()

    protected inline fun <T> Flow<T>.launch() = launchIn(screenScope)

}