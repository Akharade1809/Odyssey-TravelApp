package com.example.odyssey.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Intent : ViewIntent, State : ViewState, Effect : ViewEffect> : ViewModel() {

    private val initialState : State by lazy { setInitialState() }
    abstract fun setInitialState() : State

    private val _viewState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _effect : MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    val currentState : State
        get() = viewState.value

    protected fun setState(reducer : State.() -> State){
        val newState = currentState.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(effectValue : Effect){
        viewModelScope.launch {
            _effect.emit(effectValue)
        }
    }

    abstract fun handleIntent(intent: Intent)
}

interface ViewState
interface ViewIntent
interface ViewEffect